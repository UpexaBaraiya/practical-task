package com.example.practicaltask.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.practicaltask.R
import com.example.practicaltask.data.model.LoginRequest
import com.example.practicaltask.databinding.ActivityLoginBinding
import com.example.practicaltask.local.Preferences
import com.example.practicaltask.presentation.UserDetailActivity
import com.example.practicaltask.utils.showToast
import com.example.practicaltask.utils.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var pref: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pref = Preferences(this)

        initActions()
        initObserver()
    }

    private fun initActions() {
        binding.apply {
            loginButton.setOnClickListener {
                if (validate()) {
                    viewModel.loginUser(
                        LoginRequest(
                            username = usernameEt.text.toString().trim(),
                            password = passwordEditText.text.toString().trim(),
                            expiresInMins = "5"
                        )
                    )
                }
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.loginState.collect {

                binding.loader.isVisible = it.isLoading

                it.error?.let { e ->
                    showToast(e)
                }
                it.loginResponse?.let { response ->
                    if (binding.rememberCheckBox.isChecked) {
                        pref.putBoolean(pref.hasToRemember, true)
                        pref.putString(pref.userInfo, response.toJson())
                    }
                    startActivity(Intent(this@LoginActivity, UserDetailActivity::class.java))
                }
            }
        }
    }

    private fun validate(): Boolean {
        val username = binding.usernameEt.text.trim()
        val password = binding.passwordEditText.text.trim()

        if (username.isEmpty()) {
            this.showToast("Please enter your email")
            return false
        }

        if (password.isEmpty()) {
            showToast("Please enter your password")
            return false
        }
        return true
    }
}