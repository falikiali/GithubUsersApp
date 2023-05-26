package com.falikiali.githubusersapp.presentation.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.falikiali.githubusersapp.R
import com.falikiali.githubusersapp.databinding.ActivityDetailBinding
import com.falikiali.githubusersapp.presentation.fragment.follow.FollowersFragment
import com.falikiali.githubusersapp.presentation.fragment.follow.FollowingsFragment
import com.falikiali.githubusersapp.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initPageAdapter()
        initObserver()
        getDetailUser()
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
                }
            }

            error.observe(this@DetailActivity) {
                handleError(it)
            }
        }
    }

    private fun getDetailUser() {
        detailViewModel.getDetailUser(handleIntent())
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