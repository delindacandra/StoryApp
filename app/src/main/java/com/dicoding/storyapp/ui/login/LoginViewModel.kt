package com.dicoding.storyapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.Repository
import com.dicoding.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch


class LoginViewModel (private val repository: Repository) : ViewModel(){

    fun login(email:String, password:String) = repository.login(email, password)

    fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}