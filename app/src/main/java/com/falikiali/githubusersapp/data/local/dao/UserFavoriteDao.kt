package com.falikiali.githubusersapp.data.local.dao

import androidx.room.*
import com.falikiali.githubusersapp.data.local.entity.UserFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(userFavoriteEntity: UserFavoriteEntity)

    @Delete
    suspend fun removeFavoriteUser(userFavoriteEntity: UserFavoriteEntity)

    @Query("SELECT * FROM user_favorite")
    fun getAllFavoriteUser(): Flow<List<UserFavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user_favorite WHERE username = :username)")
    fun isUserFavorited(username: String): Flow<Boolean>

}