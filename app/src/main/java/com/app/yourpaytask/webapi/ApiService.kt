package com.app.yourpaytask.webapi

import com.app.yourpaytask.responsemodel.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

  @GET("public/v1/users")
  suspend fun getUser(): Response<UserModel>

}