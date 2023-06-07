package com.falikiali.githubusersapp.presentation.activity.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.githubusersapp.domain.model.UserFavorite
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _resultFavoriteUser = MutableLiveData<List<UserFavorite>>()
    val resultFavoriteUser: LiveData<List<UserFavorite>> get() = _resultFavoriteUser

    /**
     * Database Local
     */
    fun getAllFavoriteUser() {
        viewModelScope.launch {
            userUseCase.getAllFavoriteUser().collect {
                _resultFavoriteUser.postValue(it)
            }
        }
    }

}