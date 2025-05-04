package com.example.practicaltask.data.model

data class LoginRequest(
    val expiresInMins: String,
    val password: String,
    val username: String
)