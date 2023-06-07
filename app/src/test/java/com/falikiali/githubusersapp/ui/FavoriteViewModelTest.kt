package com.falikiali.githubusersapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.falikiali.githubusersapp.MainDispatcherRule
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.presentation.activity.favorite.FavoriteViewModel
import com.falikiali.githubusersapp.utils.DataDummyUsers
import com.falikiali.githubusersapp.utils.getAwaitOrValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val followUsers = DataDummyUsers.generateDummyFavoriteUsers()

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(userUseCase)
    }

    @Test
    fun `when get all favorite users`() = runTest {
        val expectedData = followUsers
        val dataFlow = MutableStateFlow(expectedData)
        `when`(userUseCase.getAllFavoriteUser()).thenReturn(dataFlow)

        favoriteViewModel.getAllFavoriteUser()
        verify(userUseCase).getAllFavoriteUser()

        val actualData = favoriteViewModel.resultFavoriteUser.getAwaitOrValue()
        assertEquals(expectedData, actualData)
    }

}