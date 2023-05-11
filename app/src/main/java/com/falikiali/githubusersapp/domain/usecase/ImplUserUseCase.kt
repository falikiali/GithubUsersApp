package com.falikiali.githubusersapp.domain.usecase

import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.domain.repository.UserRepository
import com.falikiali.githubusersapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImplUserUseCase @Inject constructor(private val userRepository: UserRepository) : UserUseCase {

    override suspend fun searchUser(keyword: String): Flow<ResultState<List<SearchUserItem>>> {
        return userRepository.searchUser(keyword)
    }

    override suspend fun getDetailUser(user: String): Flow<ResultState<User>> {
        return userRepository.getDetailUser(user)
    }

    override suspend fun getFollowersUser(user: String): Flow<ResultState<List<FollowUserItem>>> {
        return userRepository.getFollowersUser(user)
    }

    override suspend fun getFollowingUser(user: String): Flow<ResultState<List<FollowUserItem>>> {
        return userRepository.getFollowingsUser(user)
    }

}