package com.falikiali.githubusersapp.data.remote.api

import com.falikiali.githubusersapp.data.remote.dto.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") keyword: String): Response<SearchUserResponse>
}