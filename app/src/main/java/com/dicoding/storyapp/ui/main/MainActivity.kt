package com.dicoding.storyapp.ui.main


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.setting.ViewModelFactory
import com.dicoding.storyapp.data.ResultState
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.ui.uploadStory.UploadActivity
import com.dicoding.storyapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: ListStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListStoryAdapter()
        binding.recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.fbUpload.setOnClickListener{
            startActivity(Intent(this, UploadActivity::class.java))
        }
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showToast(message: String) {Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        viewModel.getStories().observe(this){ story ->
            when(story){
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                    if(story.data.isEmpty()){
                        showToast("Gagal memuat data")
                    }else{
                        adapter.submitList(story.data)
                    }
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showToast(story.error.toString())
                }
            }
        }
    }


}