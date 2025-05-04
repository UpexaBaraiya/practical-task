package com.example.practicaltask.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.practicaltask.R
import com.example.practicaltask.data.model.LoginResponse
import com.example.practicaltask.databinding.ActivityLoginBinding
import com.example.practicaltask.databinding.ActivityUserDetailBinding
import com.example.practicaltask.local.Preferences
import com.example.practicaltask.presentation.login.LoginActivity
import com.example.practicaltask.utils.fromJson

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var pref: Preferences
    private var userData: LoginResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pref = Preferences(this)
        userData = pref.getString(pref.userInfo)?.fromJson()

        binding.apply {

            userData?.let {
                firstNameTv.text = it.firstName
                emailTv.text = it.email
                genderTv.text = it.gender
                usernameTv.text = it.username
                lastNameTv.text = it.lastName

                Glide.with(this@UserDetailActivity).load(it.image).into(profileIV)
            }

            backIcon.setOnClickListener {
                onBackPressed()
            }

            logOutBtn.setOnClickListener {
                pref.clear()
                startActivity(Intent(this@UserDetailActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}