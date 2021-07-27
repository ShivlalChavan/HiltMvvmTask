package com.app.yourpaytask.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.yourpaytask.app.YourPayApp
import com.app.yourpaytask.repository.HomeRepository
import com.app.yourpaytask.responsemodel.UserModel
import com.app.yourpaytask.utils.hasInternetConnection
import com.app.yourpaytask.utils.toast
import com.arvind.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val repository: HomeRepository
) : AndroidViewModel(application) {


    val userData: MutableLiveData<Resource<UserModel>> = MutableLiveData()

    private val userDataTemp = MutableLiveData<Resource<UserModel>>()

    init {
           getUserData()
    }


    fun getUserData() = viewModelScope.launch {
         fetchUserData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun fetchUserData(){
        userData.postValue(Resource.Loading())
        try {
             if(hasInternetConnection<YourPayApp>()){
                 val response = repository.getUserData()
                  userDataTemp.postValue(Resource.Success(response.body()!!))
                 userData.postValue(handleNewsResponse(response))
             }else {
                 userData.postValue(Resource.Error("No Internet Connection"))
                 toast(getApplication(), "No Internet Connection.!")
             }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> userData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> userData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }


    private fun handleNewsResponse(response: Response<UserModel>): Resource<UserModel>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    
}