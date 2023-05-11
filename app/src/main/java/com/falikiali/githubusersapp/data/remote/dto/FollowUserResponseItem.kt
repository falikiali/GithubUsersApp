package com.falikiali.githubusersapp.data.remote.dto

import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.google.gson.annotations.SerializedName

data class FollowUserResponseItem(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null

) {
    fun toDomain(): FollowUserItem {
        return FollowUserItem(
            login,
            avatarUrl,
            id
        )
    }
}
