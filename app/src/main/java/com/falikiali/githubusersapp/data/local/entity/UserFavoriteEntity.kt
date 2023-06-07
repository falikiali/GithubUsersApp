package com.falikiali.githubusersapp.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.falikiali.githubusersapp.domain.model.UserFavorite
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_favorite")
data class UserFavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null
) : Parcelable {
    fun toDomain(): UserFavorite {
        return UserFavorite(
            username, avatarUrl
        )
    }
}
