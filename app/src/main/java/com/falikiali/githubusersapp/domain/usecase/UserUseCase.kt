package com.falikiali.githubusersapp.domain.usecase

import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    suspend fun searchUser(keyword: String): Flow<ResultState<List<SearchUserItem>>>

}