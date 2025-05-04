package com.example.practicaltask.presentation.numberActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaltask.R
import com.example.practicaltask.databinding.ActivityButtonTaskBinding
import com.example.practicaltask.databinding.ActivityNumbersBinding
import com.example.practicaltask.presentation.buttons.ButtonTaskActivity
import com.example.practicaltask.utils.showToast

class NumbersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumbersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNumbersBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnSave.setOnClickListener {
                val input = etNumber.text.toString()
                if (input.isNotBlank()) {
                    val number = input.toIntOrNull()
                    number?.let {
                        val intent = Intent(this@NumbersActivity, ButtonTaskActivity::class.java)
                        intent.putExtra("Number", input)
                        startActivity(intent)
                    } ?: this@NumbersActivity.showToast("Invalid number")
                }
            }
        }
    }
}