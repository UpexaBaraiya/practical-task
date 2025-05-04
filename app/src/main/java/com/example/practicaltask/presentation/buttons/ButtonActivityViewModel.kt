package com.example.practicaltask.presentation.buttons

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ButtonActivityViewModel @Inject constructor() : ViewModel() {
    enum class ButtonState {
        WHITE, BLUE, RED
    }

    private val _buttonStates = MutableStateFlow<List<ButtonState>>(emptyList())
    val buttonStates: StateFlow<List<ButtonState>> = _buttonStates.asStateFlow()

    fun initializeButtons(count: Int) {
        val list = MutableList(count) { ButtonState.WHITE }
        val randomIndex = list.indices.random()
        list[randomIndex] = ButtonState.BLUE
        _buttonStates.value = list
    }

    fun onButtonClicked(index: Int) {
        val current = _buttonStates.value.toMutableList() ?: return

        if (current[index] == ButtonState.BLUE) {
            current[index] = ButtonState.RED
            if (current.contains(ButtonState.WHITE)) {
                val whiteIndices = current.withIndex()
                    .filter { it.value == ButtonState.WHITE }
                    .map { it.index }
                val randomWhite = whiteIndices.random()
                current[randomWhite] = ButtonState.BLUE
            }
            _buttonStates.value = current
        }
    }
}
