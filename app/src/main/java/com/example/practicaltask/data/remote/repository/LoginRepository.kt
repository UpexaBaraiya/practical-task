package com.example.practicaltask.data.remote.repository

import com.example.practicaltask.data.model.LoginRequest
import com.example.practicaltask.data.model.LoginResponse
import com.example.practicaltask.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository{
    fun loginUser(request: LoginRequest) : Flow<NetworkResult<LoginResponse>>
}
