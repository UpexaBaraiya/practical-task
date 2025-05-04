package com.example.practicaltask.domain.repositories

import com.example.practicaltask.data.model.LoginRequest
import com.example.practicaltask.data.model.LoginResponse
import com.example.practicaltask.data.remote.repository.LoginRepository
import com.example.practicaltask.data.remote.service.ApiService
import com.example.practicaltask.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LoginRepository {

    override fun loginUser(request: LoginRequest): Flow<NetworkResult<LoginResponse>> {
        return flow {
            try {
                val data = apiService.loginUser(request)

                if (data.isSuccessful) {
                    if (data.body() != null) {
                        emit(NetworkResult.Success(data.body()!!))
                    } else {
                        emit(NetworkResult.Error(data.message()))
                    }
                } else {
                    emit(NetworkResult.Error(data.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.orEmpty()))
            }
        }.flowOn(Dispatchers.IO)
    }
}