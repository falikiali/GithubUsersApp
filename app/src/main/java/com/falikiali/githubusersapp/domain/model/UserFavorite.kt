package com.falikiali.githubusersapp.domain.model

import com.falikiali.githubusersapp.data.local.entity.UserFavoriteEntity

data class UserFavorite(
    val username: String,
    val avatarUrl: String? = null
) {
    fun toEntity(): UserFavoriteEntity {
        return UserFavoriteEntity(
            username, avatarUrl
        )
    }
}
