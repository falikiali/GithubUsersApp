package com.falikiali.githubusersapp.presentation.activity.detail

import androidx.lifecycle.*
import com.falikiali.githubusersapp.data.local.entity.UserFavoriteEntity
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.domain.model.UserFavorite
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _resultUser = MutableLiveData<User>()
    val resultUser: LiveData<User> get() = _resultUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> get() = _isFavorited

    /**
     * Remote
     */
    fun getDetailUser(user: String) {
        viewModelScope.launch {
            userUseCase.getDetailUser(user).collect {
                when (it) {
                    is ResultState.Success -> _resultUser.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(it.error)
                    ResultState.Empty -> TODO()
                }
            }
        }
    }

    /**
     * Database Local
     */
    fun isUserFavorited(username: String) {
        viewModelScope.launch {
            userUseCase.isUserFavorited(username).collect {
                _isFavorited.postValue(it)
            }
        }
    }

    fun updateFavoriteUser(userFavorite: UserFavorite) {
        viewModelScope.launch {
            if (isFavorited.value == true) {
                userUseCase.removeFavoriteUser(userFavorite)
            } else {
                userUseCase.insertFavoriteUser(userFavorite)
            }
        }
    }

}