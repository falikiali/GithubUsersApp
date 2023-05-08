package com.falikiali.githubusersapp.data

import com.falikiali.githubusersapp.data.remote.api.ApiService
import com.falikiali.githubusersapp.data.remote.dto.SearchUserResponse
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.repository.UserRepository
import com.falikiali.githubusersapp.utils.ResultState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ImplUserRepository @Inject constructor(private val apiService: ApiService) : UserRepository {
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
}