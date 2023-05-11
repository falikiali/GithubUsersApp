package com.falikiali.githubusersapp.presentation.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.githubusersapp.domain.model.User
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _resultUser = MutableLiveData<User>()
    val resultUser: LiveData<User> get() = _resultUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

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

}