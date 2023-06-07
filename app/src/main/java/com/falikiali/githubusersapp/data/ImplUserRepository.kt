package com.falikiali.githubusersapp.data

import com.falikiali.githubusersapp.data.local.dao.UserFavoriteDao
import com.falikiali.githubusersapp.data.remote.api.ApiService
import com.falikiali.githubusersapp.data.remote.dto.FollowUserResponseItem
import com.falikiali.githubusersapp.data.remote.dto.SearchUserResponse
import com.falikiali.githubusersapp.data.remote.dto.UserResponse
import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.domain.model.UserFavorite
import com.falikiali.githubusersapp.domain.repository.UserRepository
import com.falikiali.githubusersapp.utils.ResultState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ImplUserRepository @Inject constructor(private val apiService: ApiService, private val userFavoriteDao: UserFavoriteDao) : UserRepository {
    override suspend fun searchUser(keyword: String): Flow<ResultState<List<SearchUserItem>>> {
        return flow {
            try {
                val response = apiService.searchUsers(keyword)
                if (response.isSuccessful) {
                    if (response.body()?.totalCount != 0) {
                        val data = response.body()!!.items!!.map { it.toDomain() }
                        emit(ResultState.Success(data))
                    } else {
                        emit(ResultState.Empty)
                    }
                } else {
                    val type = object : TypeToken<SearchUserResponse>(){}.type
                    val err = Gson().fromJson<SearchUserResponse>(response.errorBody()!!.charStream(), type)!!
                    emit(ResultState.Failed(err.message.toString(), response.code()))
                }
            } catch (e: HttpException) {
                emit(ResultState.Failed("Server Error", null))
            } catch (e: IOException) {
                emit(ResultState.Failed("Error Occurred", null))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.localizedMessage ?: "An Unexpected Error", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailUser(user: String): Flow<ResultState<User>> {
        return flow {
            try {
                val response = apiService.getDetailUser(user)
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomain()
                    emit(ResultState.Success(data))
                } else {
                    val type = object : TypeToken<UserResponse>(){}.type
                    val err = Gson().fromJson<UserResponse>(response.errorBody()!!.charStream(), type)!!
                    emit(ResultState.Failed(err.message.toString(), response.code()))
                }
            } catch (e: HttpException) {
                emit(ResultState.Failed("Server Error", null))
            } catch (e: IOException) {
                emit(ResultState.Failed("Error Occurred", null))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.localizedMessage ?: "An Unexpected Error", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFollowersUser(user: String): Flow<ResultState<List<FollowUserItem>>> {
        return flow {
            try {
                val response = apiService.getFollowersUser(user)
                if (response.isSuccessful) {
                    if (response.body()?.isNotEmpty() == true) {
                        val data = response.body()!!.map { it.toDomain() }
                        emit(ResultState.Success(data))
                    } else {
                        emit(ResultState.Empty)
                    }
                } else {
                    val type = object : TypeToken<FollowUserResponseItem>(){}.type
                    val err = Gson().fromJson<FollowUserResponseItem>(response.errorBody()!!.charStream(), type)!!
                    emit(ResultState.Failed(err.message.toString(), response.code()))
                }
            } catch (e: HttpException) {
                emit(ResultState.Failed("Server Error", null))
            } catch (e: IOException) {
                emit(ResultState.Failed("Error Occurred", null))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.localizedMessage ?: "An Unexpected Error", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFollowingsUser(user: String): Flow<ResultState<List<FollowUserItem>>> {
        return flow {
            try {
                val response = apiService.getFollowingsUser(user)
                if (response.isSuccessful) {
                    if (response.body()?.isNotEmpty() == true) {
                        val data = response.body()!!.map { it.toDomain() }
                        emit(ResultState.Success(data))
                    } else {
                        emit(ResultState.Empty)
                    }
                } else {
                    val type = object : TypeToken<FollowUserResponseItem>(){}.type
                    val err = Gson().fromJson<FollowUserResponseItem>(response.errorBody()!!.charStream(), type)!!
                    emit(ResultState.Failed(err.message.toString(), response.code()))
                }
            } catch (e: HttpException) {
                emit(ResultState.Failed("Server Error", null))
            } catch (e: IOException) {
                emit(ResultState.Failed("Error Occurred", null))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.localizedMessage ?: "An Unexpected Error", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertFavoriteUser(userFavorite: UserFavorite) {
        userFavoriteDao.insertFavoriteUser(userFavorite.toEntity())
    }

    override suspend fun removeFavoriteUser(userFavorite: UserFavorite) {
        userFavoriteDao.removeFavoriteUser(userFavorite.toEntity())
    }

    override fun getAllFavoriteUser(): Flow<List<UserFavorite>> {
        return userFavoriteDao.getAllFavoriteUser().map { list ->
            list.map { user ->
                user.toDomain()
            }
        }
    }

    override fun isUserFavorited(username: String): Flow<Boolean> {
        return userFavoriteDao.isUserFavorited(username)
    }
}