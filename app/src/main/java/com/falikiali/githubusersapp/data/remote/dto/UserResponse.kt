package com.falikiali.githubusersapp.data.remote.dto

import com.falikiali.githubusersapp.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: String? = null,

    @field:SerializedName("public_repos")
    val publicRepos: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null

) {
    fun toDomain(): User {
        return User(
            name,
            username,
            location,
            company,
            followers,
            following,
            publicRepos,
            avatarUrl
        )
    }
}
