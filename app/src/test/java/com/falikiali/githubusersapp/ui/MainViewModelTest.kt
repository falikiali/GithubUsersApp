package com.falikiali.githubusersapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.MutableState
import com.falikiali.githubusersapp.MainDispatcherRule
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.presentation.activity.main.MainViewModel
import com.falikiali.githubusersapp.utils.DataDummyUsers
import com.falikiali.githubusersapp.utils.ResultState
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    private lateinit var mainViewModel: MainViewModel
    private val dummyData = DataDummyUsers.generateDummySearchUser()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(userUseCase)
    }

    @Test
    fun `when successfully search Users`() = runTest {
        val expectedData = ResultState.Success(dummyData)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.searchUser("test")).thenReturn(dataFlow)
        mainViewModel.searchUser("test")
        verify(userUseCase).searchUser("test")

        val actualData = mainViewModel.resultSearchUser.getAwaitOrValue()
        val isLoading = mainViewModel.isLoading.getAwaitOrValue()

        assertNotNull(actualData)
        assertEquals(expectedData.data, actualData)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when successfully search Users but empty`() = runTest {
        val expectedData = ResultState.Empty
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.searchUser("test")).thenReturn(dataFlow)
        mainViewModel.searchUser("test")
        verify(userUseCase).searchUser("test")

        val actualData = mainViewModel.isEmpty.getAwaitOrValue()
        val isLoading = mainViewModel.isLoading.getAwaitOrValue()

        assertTrue(actualData == true)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when failed search Users`() = runTest {
        val expectedData = ResultState.Failed("Internal server error", 500)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.searchUser("test")).thenReturn(dataFlow)
        mainViewModel.searchUser("test")
        verify(userUseCase).searchUser("test")

        val actualData = mainViewModel.error.getAwaitOrValue()
        val isLoading = mainViewModel.isLoading.getAwaitOrValue()

        assertEquals("Internal server error", actualData)
        assertTrue(isLoading == false)
    }

}