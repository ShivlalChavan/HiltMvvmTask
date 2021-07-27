package com.app.yourpaytask.repository

import com.app.yourpaytask.webapi.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService)
{
    suspend fun getUserData() = apiService.getUser()
}