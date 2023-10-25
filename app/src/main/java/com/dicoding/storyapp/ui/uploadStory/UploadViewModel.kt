package com.dicoding.storyapp.ui.uploadStory

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.Repository
import java.io.File

class UploadViewModel(private val repository: Repository) : ViewModel(){
    fun addStories(file: File, description: String) = repository.addStories(file, description)
}