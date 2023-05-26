package com.falikiali.githubusersapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.falikiali.githubusersapp.MainDispatcherRule
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import com.falikiali.githubusersapp.presentation.activity.detail.DetailViewModel
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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    private lateinit var detailViewModel: DetailViewModel
    private val dummy = DataDummyUsers.generateDummyDetailUser()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(userUseCase)
    }

    @Test
    fun `when successfully get User Detail`() = runTest {
        val expectedData = ResultState.Success(dummy)
        val dataFlow = MutableStateFlow(expectedData)

        `when`(userUseCase.getDetailUser("test")).thenReturn(dataFlow)
        detailViewModel.getDetailUser("test")
        verify(userUseCase).getDetailUser("test")

        val actualData = detailViewModel.resultUser.getAwaitOrValue()
        assertNotNull(actualData)
        assertEquals(dummy, actualData)
    }
}