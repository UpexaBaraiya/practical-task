package com.example.practicaltask

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaltask.databinding.ActivityMainBinding
import com.example.practicaltask.local.Preferences
import com.example.practicaltask.presentation.UserDetailActivity
import com.example.practicaltask.presentation.login.LoginActivity
import com.example.practicaltask.presentation.numberActivity.NumbersActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var pref: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pref = Preferences(this)

        binding.apply {
            taskOneBtn.setOnClickListener {
                if (pref.getBoolean(pref.hasToRemember) == true) {
                    startActivity(Intent(this@MainActivity, UserDetailActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
            }
            taskTwoBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, NumbersActivity::class.java))
            }
        }
    }
}