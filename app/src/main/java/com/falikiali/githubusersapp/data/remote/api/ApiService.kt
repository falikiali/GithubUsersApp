package com.falikiali.githubusersapp.data.remote.api

import com.falikiali.githubusersapp.data.remote.dto.FollowUserResponseItem
import com.falikiali.githubusersapp.data.remote.dto.SearchUserResponse
import com.falikiali.githubusersapp.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") keyword: String): Response<SearchUserResponse>

    @GET("users/{user}")
    suspend fun getDetailUser(@Path("user") user: String): Response<UserResponse>

    @GET("users/{user}/followers")
    suspend fun getFollowersUser(@Path("user") user: String): Response<List<FollowUserResponseItem>>

    @GET("users/{user}/following")
    suspend fun getFollowingsUser(@Path("user") user: String): Response<List<FollowUserResponseItem>>
}