package com.falikiali.githubusersapp.utils

import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.domain.model.UserFavorite

object DataDummyUsers {

    fun generateDummyFollowUser(): List<FollowUserItem> {
        val list = arrayOf(
            FollowUserItem(
                "users-1",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpublikasi.ojk.go.id%2FPortal%2FViewDetailIndividual%2F6&psig=AOvVaw2xSWmoFSuLbHwYEyqkf8Vf&ust=1685072104071000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJiQ7vLEj_8CFQAAAAAdAAAAABAE",
                1
            ),
            FollowUserItem(
                "users-2",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpublikasi.ojk.go.id%2FPortal%2FViewDetailIndividual%2F6&psig=AOvVaw2xSWmoFSuLbHwYEyqkf8Vf&ust=1685072104071000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJiQ7vLEj_8CFQAAAAAdAAAAABAE",
                2
            ),
            FollowUserItem(
                "users-3",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpublikasi.ojk.go.id%2FPortal%2FViewDetailIndividual%2F6&psig=AOvVaw2xSWmoFSuLbHwYEyqkf8Vf&ust=1685072104071000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJiQ7vLEj_8CFQAAAAAdAAAAABAE",
                3
            )
        )

        val followUserList = ArrayList<FollowUserItem>()
        followUserList.addAll(list)

        return followUserList
    }

    fun generateDummySearchUser(): List<SearchUserItem> {
        val list = arrayOf(
            SearchUserItem(
                "users-1",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpublikasi.ojk.go.id%2FPortal%2FViewDetailIndividual%2F6&psig=AOvVaw2xSWmoFSuLbHwYEyqkf8Vf&ust=1685072104071000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJiQ7vLEj_8CFQAAAAAdAAAAABAE",
                1
            )
        )

        val searchUserList = ArrayList<SearchUserItem>()
        searchUserList.addAll(list)

        return searchUserList
    }

    fun generateDummyDetailUser(): User {
        return User(
            "String",
            "String",
            "String",
            "String",
            100,
            100,
            1000,
            "String",
        )
    }

    fun generateDummyFavoriteUser(): UserFavorite {
        return UserFavorite(
            "String",
            "String"
        )
    }

    fun generateDummyFavoriteUsers(): List<UserFavorite> {
        val list = arrayOf(
            UserFavorite(
                "String",
                "String"
            ),
            UserFavorite(
                "String",
                "String"
            ),
            UserFavorite(
                "String",
                "String"
            )
        )

        val favoriteUserList = ArrayList<UserFavorite>()
        favoriteUserList.addAll(list)

        return favoriteUserList
    }

}