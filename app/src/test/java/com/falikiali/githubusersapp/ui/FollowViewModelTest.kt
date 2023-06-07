package com.falikiali.githubusersapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.falikiali.githubusersapp.MainDispatcherRule
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.presentation.fragment.follow.FollowViewModel
import com.falikiali.githubusersapp.utils.DataDummyUsers
import com.falikiali.githubusersapp.utils.ResultState
import com.falikiali.githubusersapp.utils.getAwaitOrValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FollowViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    private lateinit var followViewModel: FollowViewModel
    private val followUsers = DataDummyUsers.generateDummyFollowUser()

    @Before
    fun setUp() {
        followViewModel = FollowViewModel(userUseCase)
    }

    /**
     * Followers User
     */
    @Test
    fun `when successfully get Followers User`() = runTest {
        val expectedData = ResultState.Success(followUsers)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowersUser("test")).thenReturn(dataFlow)

        followViewModel.getFollowersUser("test")
        verify(userUseCase).getFollowersUser("test")

        val actualData = followViewModel.resultFollow.getAwaitOrValue()
        val isLoading = followViewModel.isLoading.getAwaitOrValue()

        assertEquals(followUsers, actualData)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when successfully get Followers User but Empty`() = runTest {
        val expectedData = ResultState.Empty
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowersUser("test")).thenReturn(dataFlow)

        followViewModel.getFollowersUser("test")
        verify(userUseCase).getFollowersUser("test")

        val actualData = followViewModel.isEmpty.getAwaitOrValue()
        val isLoading = followViewModel.isLoading.getAwaitOrValue()

        assertTrue(actualData == true)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when failed get Followers User`() = runTest {
        val expectedData = ResultState.Failed("Internal server error", 500)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowersUser("test")).thenReturn(dataFlow)

        followViewModel.getFollowersUser("test")
        verify(userUseCase).getFollowersUser("test")

        val actualData = followViewModel.error.getAwaitOrValue()
        val isLoading = followViewModel.isLoading.getAwaitOrValue()

        assertNotNull(actualData)
        assertEquals("Internal server error", actualData)
        assertTrue(isLoading == false)
    }

    /**
     * Following User
     */
    @Test
    fun `when successfully get Following User`() = runTest {
        val expectedData = ResultState.Success(followUsers)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowingUser("test")).thenReturn(dataFlow)
        followViewModel.getFollowingUser("test")
        verify(userUseCase).getFollowingUser("test")

        val actualData = followViewModel.resultFollow.getAwaitOrValue()
        val isLoading = followViewModel.isLoading.getAwaitOrValue()

        assertEquals(followUsers, actualData)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when successfully get Following User but Empty`() = runTest {
        val expectedData = ResultState.Empty
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowingUser("test")).thenReturn(dataFlow)
        followViewModel.getFollowingUser("test")
        verify(userUseCase).getFollowingUser("test")

        val isLoading = followViewModel.isLoading.getAwaitOrValue()
        val actualData = followViewModel.isEmpty.getAwaitOrValue()

        assertTrue(actualData == true)
        assertTrue(isLoading == false)
    }

    @Test
    fun `when failed get Following User`() = runTest {
        val expectedData = ResultState.Failed("Internal server error", 500)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getFollowingUser("test")).thenReturn(dataFlow)
        followViewModel.getFollowingUser("test")
        verify(userUseCase).getFollowingUser("test")

        val actualData = followViewModel.error.getAwaitOrValue()
        val isLoading = followViewModel.isLoading.getAwaitOrValue()

        assertNotNull(actualData)
        assertEquals("Internal server error", actualData)
        assertTrue(isLoading == false)
    }

}