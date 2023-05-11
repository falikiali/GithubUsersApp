package com.falikiali.githubusersapp.domain.repository

import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUser(keyword: String): Flow<ResultState<List<SearchUserItem>>>

    suspend fun getDetailUser(user: String): Flow<ResultState<User>>

    suspend fun getFollowersUser(user: String): Flow<ResultState<List<FollowUserItem>>>

    suspend fun getFollowingsUser(user: String): Flow<ResultState<List<FollowUserItem>>>

}