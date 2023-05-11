package com.falikiali.githubusersapp.domain.model

data class User(
    val name: String? = null,
    val username: String? = null,
    val location: String? = null,
    val company: String? = null,
    val followers: Int? = null,
    val following: String? = null,
    val publicRepos: String? = null,
    val avatarUrl: String? = null
)
