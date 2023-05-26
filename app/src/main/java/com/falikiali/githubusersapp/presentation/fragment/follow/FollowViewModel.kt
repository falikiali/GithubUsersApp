package com.falikiali.githubusersapp.presentation.fragment.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.githubusersapp.domain.model.FollowUserItem
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _resultFollowers = MutableLiveData<List<FollowUserItem>>()
    val resultFollowers: LiveData<List<FollowUserItem>> get() = _resultFollowers

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getFollowersUser(user: String) {
        _isEmpty.value = false
        _isLoading.value = true

        viewModelScope.launch {
            userUseCase.getFollowersUser(user).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultFollowers.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(it.error)
                    is ResultState.Empty -> _isEmpty.value = true
                }
            }
        }
    }

    fun getFollowingUser(user: String) {
        _isEmpty.value = false
        _isLoading.value = true

        viewModelScope.launch {
            userUseCase.getFollowingUser(user).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultFollowers.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(it.error)
                    is ResultState.Empty -> _isEmpty.value = true
                }
            }
        }
    }

}