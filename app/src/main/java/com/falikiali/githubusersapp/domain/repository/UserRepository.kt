package com.falikiali.githubusersapp.domain.repository

import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun searchUser(keyword: String): Flow<ResultState<List<SearchUserItem>>>

}