package com.dicoding.storyapp.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.Repository

class SignupViewModel(private val repository: Repository) : ViewModel() {

    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}