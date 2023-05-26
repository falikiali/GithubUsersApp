package com.falikiali.githubusersapp.domain.model

data class User(
    val name: String? = null,
    val username: String? = null,
    val location: String? = null,
    val company: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val publicRepos: Int? = null,
    val avatarUrl: String? = null
)
