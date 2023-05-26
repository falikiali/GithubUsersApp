package com.falikiali.githubusersapp.presentation.fragment.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.githubusersapp.databinding.FragmentFollowingsBinding
import com.falikiali.githubusersapp.presentation.activity.detail.DetailActivity
import com.falikiali.githubusersapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingsFragment : Fragment() {

    private val followViewModel: FollowViewModel by viewModels()
    private val binding: FragmentFollowingsBinding by lazy {
        FragmentFollowingsBinding.inflate(layoutInflater)
    }
    private val followAdapter: FollowAdapter by lazy {
        FollowAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFollowingUser()
        initRecyclerView()
        initObserver()
    }

    private fun getFollowingUser() {
        val user = requireActivity().intent.getStringExtra(DetailActivity.USERNAME_KEY)
        followViewModel.getFollowingUser(user!!)
    }

    private fun initRecyclerView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = followAdapter
            setHasFixedSize(true)
        }
    }

    private fun initObserver() {
        with(followViewModel) {
            resultFollowers.observe(viewLifecycleOwner) {
                followAdapter.setItems(it)
                handleResultFollowing(true)
            }

            isEmpty.observe(viewLifecycleOwner) {
                handleEmptyUser(it)
                handleResultFollowing(!it)
            }

            isLoading.observe(viewLifecycleOwner) {
                handleLoading(it)
                handleResultFollowing(!it)
            }

            error.observe(viewLifecycleOwner) {
                handleError(it)
                handleResultFollowing(false)
            }
        }
    }



    private fun handleResultFollowing(status: Boolean) {
        binding.rvUsers.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleEmptyUser(isEmpty: Boolean) {
        binding.empty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun handleError(message: String) {
        Utils.showToast(message, requireActivity())
    }

}