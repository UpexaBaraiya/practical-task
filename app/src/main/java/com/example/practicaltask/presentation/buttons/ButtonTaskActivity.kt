package com.example.practicaltask.presentation.buttons

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practicaltask.R
import com.example.practicaltask.databinding.ActivityButtonTaskBinding
import com.example.practicaltask.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ButtonTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityButtonTaskBinding
    private lateinit var buttonContainer: LinearLayout
    private var numButtons = 0
    val viewModel: ButtonActivityViewModel by viewModels()
    private val buttons = mutableListOf<Button>()

    private val WHITE = Color.WHITE
    private val BLUE = Color.parseColor("#2196F3")
    private val RED = Color.RED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityButtonTaskBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonContainer = findViewById(R.id.buttonContainer)
        numButtons = intent.extras?.getString("Number")?.toInt() ?: 0

        binding.apply {
            viewModel.initializeButtons(numButtons)
        }

        lifecycleScope.launch {
            viewModel.buttonStates.collect { states ->
                if (buttons.isEmpty()) {
                    createButtons(states.size)
                }
                updateButtons(states)
            }
        }
    }

    private fun createButtons(count: Int) {
        repeat(count) { index ->
            val button = Button(this).apply {
                text = "${index + 1}"
                layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 90).apply {
                    setMargins(8, 8, 8, 8)
                }
                setOnClickListener {
                    viewModel.onButtonClicked(index)
                }
            }
            buttons.add(button)
            buttonContainer.addView(button)
        }
    }

    private fun updateButtons(states: List<ButtonActivityViewModel.ButtonState>) {
        buttons.forEachIndexed { index, button ->
            val color = when (states[index]) {
                ButtonActivityViewModel.ButtonState.WHITE -> WHITE
                ButtonActivityViewModel.ButtonState.BLUE -> BLUE
                ButtonActivityViewModel.ButtonState.RED -> RED
            }
            button.setBackgroundColor(color)
        }
    }
}