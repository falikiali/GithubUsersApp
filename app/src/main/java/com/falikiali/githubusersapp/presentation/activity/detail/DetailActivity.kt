package com.falikiali.githubusersapp.presentation.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.falikiali.githubusersapp.R
import com.falikiali.githubusersapp.databinding.ActivityDetailBinding
import com.falikiali.githubusersapp.domain.model.UserFavorite
import com.falikiali.githubusersapp.presentation.fragment.follow.FollowersFragment
import com.falikiali.githubusersapp.presentation.fragment.follow.FollowingsFragment
import com.falikiali.githubusersapp.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private var username: String? = null
    private var avatar: String? = null
    private var favorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initPageAdapter()
        initObserver()
        getDetailUser()
        updateFavoriteUser()
        checkFavoriteUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
            title = "${handleIntent()}'s Profile"
        }
    }

    private fun initPageAdapter() {
        val fragment = mutableListOf(
            FollowingsFragment(),
            FollowersFragment()
        )

        val fragmentTitle = mutableListOf(
            getString(R.string.following),
            getString(R.string.followers)
        )

        val sectionPagerAdapter = DetailViewPagerAdapter(this, fragment)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = fragmentTitle[position]
            }.attach()
        }
    }

    private fun initObserver() {
        with(detailViewModel) {
            resultUser.observe(this@DetailActivity) {
                with(binding) {
                    tvName.text = it.name
                    tvUsername.text = it.username
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvRepository.text = it.publicRepos.toString()
                    tvLocation.text = it.location
                    tvCompany.text = it.company

                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(ivAvatar)

                    username = it.username
                    avatar = it.avatarUrl
                }
            }

            error.observe(this@DetailActivity) {
                handleError(it)
            }

            isFavorited.observe(this@DetailActivity) { status ->
                handleFavoriteUser(status)
                favorite = status
            }
        }
    }

    private fun getDetailUser() {
        detailViewModel.getDetailUser(handleIntent())
    }

    private fun checkFavoriteUser() {
        detailViewModel.isUserFavorited(handleIntent())
    }

    private fun handleFavoriteUser(status: Boolean) {
        if (status) {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun updateFavoriteUser() {
        binding.fabFavorite.setOnClickListener {
            if (username != null) {
                detailViewModel.updateFavoriteUser(UserFavorite(username!!, avatar))
                checkFavoriteUser()

                if (favorite) {
                    Snackbar.make(binding.root, "User telah dihapus dari Favorite User", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "Tersimpan di dalam Favorite User", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleIntent(): String {
        return this.intent.getStringExtra(USERNAME_KEY)!!
    }

    private fun handleError(msg: String) {
        Utils.showToast(msg, this@DetailActivity)
    }

    companion object {
        const val USERNAME_KEY = "username_key"
    }
}