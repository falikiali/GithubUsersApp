package com.falikiali.githubusersapp.presentation.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.githubusersapp.domain.model.SearchUserItem
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _resultSearchUser = MutableLiveData<List<SearchUserItem>>()
    val resultSearchUser: LiveData<List<SearchUserItem>> get() = _resultSearchUser

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchUser(keyword: String) {
        _isEmpty.value = false
        _isLoading.value = true

        viewModelScope.launch {
            userUseCase.searchUser(keyword).collect {
                when (it) {
                    is ResultState.Success -> {
                        _isLoading.value = false
                        _resultSearchUser.postValue(it.data)
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                        _error.postValue(it.error)
                    }

                    is ResultState.Empty -> {
                        _isLoading.value = false
                        _isEmpty.value = true
                    }
                }
            }
        }
    }

}