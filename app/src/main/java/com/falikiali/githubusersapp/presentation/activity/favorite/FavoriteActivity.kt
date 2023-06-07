package com.falikiali.githubusersapp.presentation.activity.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.githubusersapp.databinding.ActivityFavoriteBinding
import com.falikiali.githubusersapp.presentation.activity.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionBar()
        initRecyclerView()
        initObserver()
        actionClickEachItem()
        getAllFavoriteUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            elevation = 0f
            title = "User Favorite"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initRecyclerView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun initObserver() {
        with(favoriteViewModel) {
            resultFavoriteUser.observe(this@FavoriteActivity) {
                if (it.isEmpty()) {
                    handleEmptyUser(true)
                } else {
                    handleEmptyUser(false)
                    favoriteAdapter.setItems(it)
                }
            }
        }
    }

    private fun actionClickEachItem() {
        favoriteAdapter.onItemClick = {
            val i = Intent(this@FavoriteActivity, DetailActivity::class.java)
            i.putExtra(DetailActivity.USERNAME_KEY, it.username)
            startActivity(i)
        }
    }

    private fun getAllFavoriteUser() {
        favoriteViewModel.getAllFavoriteUser()
    }

    private fun handleEmptyUser(isEmpty: Boolean) {
        binding.empty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}