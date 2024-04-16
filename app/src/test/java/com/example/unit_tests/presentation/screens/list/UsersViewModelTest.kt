package com.example.unit_tests.presentation.screens.list

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.useCases.getUserListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class UsersViewModelTest {
    private val getUserListUseCase = mock<getUserListUseCase>()
    @AfterEach
    fun clean(){
        Mockito.reset(getUserListUseCase)

    }
    @Test
    fun `getUsersList should update Users and FilteredUsers`() {
        Dispatchers.setMain(Dispatchers.Default)
        val viewModel = UsersViewModel(getUserListUseCase)
        val userList = listOf(User(login = "testUser1", pwd = "testUser1", userName = "testUser1"))

        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(flowOf(userList))

        viewModel.changeFilterText("")

        assertEquals(userList, viewModel.FilteredUsers.value)
        Dispatchers.resetMain()

    }

}