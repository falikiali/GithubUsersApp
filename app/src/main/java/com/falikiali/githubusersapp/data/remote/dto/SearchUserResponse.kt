package com.falikiali.githubusersapp.data.remote.dto

import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<SearchUserResponseItem>? = null

)

data class SearchUserResponseItem(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

) {
    fun toDomain(): SearchUserItem {
        return SearchUserItem(
            login,
            avatarUrl,
            id
        )
    }
}


