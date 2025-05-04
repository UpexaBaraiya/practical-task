package com.example.practicaltask.utils

import com.example.practicaltask.data.model.LoginResponse

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val errorBody: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String? = null, data: T? = null,errorBody: String? = null) : NetworkResult<T>(data, message,errorBody)
}
