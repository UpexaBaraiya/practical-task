package com.example.practicaltask.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaltask.data.model.LoginRequest
import com.example.practicaltask.data.model.LoginResponse
import com.example.practicaltask.data.remote.repository.LoginRepository
import com.example.practicaltask.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _loginState =
        MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            repository.loginUser(request)
                .onStart {
                    _loginState.value = LoginState(isLoading = true)
                }
                .catch { e ->
                    _loginState.value = LoginState(error = e.message, isLoading = false)
                }
                .collect { result ->
                    when(result){
                        is NetworkResult.Success -> {
                            _loginState.value = LoginState(loginResponse = result.data, isLoading = false)
                        }

                        is NetworkResult.Error -> {
                            _loginState.value = LoginState(error = result.message, isLoading = false)
                        }
                    }
                }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String? = null
)