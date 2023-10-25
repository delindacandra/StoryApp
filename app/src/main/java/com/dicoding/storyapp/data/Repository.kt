package com.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyapp.data.api.AddStoryResponse
import com.dicoding.storyapp.data.api.ApiService
import com.dicoding.storyapp.data.api.ErrorResponse
import com.dicoding.storyapp.data.api.ListStoryItem
import com.dicoding.storyapp.data.api.LoginResponse
import com.dicoding.storyapp.data.api.RegisterResponse
import com.dicoding.storyapp.data.pref.UserModel
import com.dicoding.storyapp.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
class Repository constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){

    fun addStories(file: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.addStory(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, AddStoryResponse::class.java)
                emit(ResultState.Error(errorBody.message))

        }
    }

    fun stories() : LiveData<ResultState<List<ListStoryItem>>> = liveData{
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.stories()
            emit(ResultState.Success(successResponse.listStory))
        }catch (e: Exception){
            if(e is HttpException){
                val jsonInString = e. response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                emit(ResultState.Error(errorBody.message))
            }else{
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            if(e is HttpException){
                val jsonInString = e. response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                emit(ResultState.Error(errorBody.message))
            }else{
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    fun register(nama: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(nama, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }



    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }


}