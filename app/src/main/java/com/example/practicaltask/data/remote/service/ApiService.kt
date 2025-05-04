package com.example.practicaltask.data.remote.service

import com.example.practicaltask.data.model.LoginRequest
import com.example.practicaltask.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}
