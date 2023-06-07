package com.falikiali.githubusersapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.falikiali.githubusersapp.data.local.dao.UserFavoriteDao
import com.falikiali.githubusersapp.data.local.entity.UserFavoriteEntity

@Database(
    entities = [UserFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userFavoriteDao(): UserFavoriteDao

}