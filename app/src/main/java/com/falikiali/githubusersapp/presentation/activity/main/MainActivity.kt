package com.falikiali.githubusersapp.presentation.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.githubusersapp.utils.Utils.hideKeyboard
import com.falikiali.githubusersapp.utils.Utils.showToast
import com.falikiali.githubusersapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter()
    }

    private var touchPosition: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initRecyclerView()
        initObserver()
        searchUser()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPosition = event.y
            }
            MotionEvent.ACTION_UP -> {
                val releasePosition = event.y
                val count = touchPosition?.minus(releasePosition)

                if (count != 0f) {
                    hideKeyboard()
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun initToolbar() {
        supportActionBar?.elevation = 0f
    }

    private fun initRecyclerView() {
        binding.content.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    private fun initObserver() {
        with(mainViewModel) {
            resultSearchUser.observe(this@MainActivity) {
                mainAdapter.setItems(it)
                handleResultSearchUser(true)
            }

            isEmpty.observe(this@MainActivity) {
                handleEmptyUser(it)
                handleResultSearchUser(!it)
            }

            isLoading.observe(this@MainActivity) {
                handleLoading(it)
                handleResultSearchUser(!it)
            }

            error.observe(this@MainActivity) {
                handleError(it)
                handleResultSearchUser(false)
            }
        }
    }

    private fun searchUser() {
        with(binding.svSearch) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (query.isNotEmpty()) {
                            mainViewModel.searchUser(query)
                        } else {
                            clearFocus()
                            handleResultSearchUser(false)
                            handleEmptyUser(true)
                        }
                    }
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    query?.let {
                        if (query.isEmpty()) {
                            handleResultSearchUser(false)
                            handleEmptyUser(true)
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun handleResultSearchUser(status: Boolean) {
        binding.content.root.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleEmptyUser(isEmpty: Boolean) {
        binding.empty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun handleError(message: String) {
        showToast(message, this@MainActivity)
    }
}