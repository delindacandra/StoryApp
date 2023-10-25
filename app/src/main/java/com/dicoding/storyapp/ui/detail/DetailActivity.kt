package com.dicoding.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.dicoding.storyapp.data.api.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityDetailBinding

@Suppress("DEPRECATION", "SameParameterValue")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
        if(data == null){
            showLoading(true)
        }else{
            showLoading(false)
            val data = intent.getParcelableExtra<ListStoryItem>(data)
            binding.apply{
                tvItemName.text = data?.name
                tvItemDescription.text = data?.description
                Glide.with(this@DetailActivity)
                    .load(data?.photoUrl)
                    .into(imgItemPhoto)
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressIndicator.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val data = "id"
    }
}