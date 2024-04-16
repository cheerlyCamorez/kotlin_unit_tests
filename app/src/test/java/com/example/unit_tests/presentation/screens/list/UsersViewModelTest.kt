package com.example.unit_tests.presentation.screens.list


import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.useCases.getUserListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
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
    fun `1 getUsersList should update Users and FilteredUsers`() {


        val userList = listOf(User(login = "testUser1", pwd = "testUser1", userName = "testUser1"))

        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(MutableStateFlow(userList))
        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("testUser1")
        Dispatchers.resetMain()

        assertEquals(userList, viewModel.FilteredUsers.value)

    }
    @Test
    fun `2 getUsersList should return empty filtered list  `() {


        val userList = listOf(User(login = "testUser1", pwd = "testUser1", userName = "testUser1"))
        val emptyUserList : List<User> = emptyList()
        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(MutableStateFlow(userList))
        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("testUser2")
        Dispatchers.resetMain()
        //проверка на вывод пустого списка, так как по фильтру такого нет
        assertEquals(emptyUserList, viewModel.FilteredUsers.value)
        //вывод фильтра
        assertEquals("testUser2" , viewModel.filterText.value)
    }
    @Test
    fun `3 getUsersList should return 1 element of filtered list`() {


        val userList = listOf(
            User(login = "testUser1", pwd = "testUser1", userName = "testUser1"),
            User(login = "testUser2", pwd = "testUser2", userName = "testUser2")
        )

        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(MutableStateFlow(userList))
        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("testUser2")
        Dispatchers.resetMain()
        //проверка на вывод пустого списка, так как по фильтру такого нет
        assertEquals(listOf(userList[1]), viewModel.FilteredUsers.value)
        //вывод фильтра
        assertEquals("testUser2" , viewModel.filterText.value)
    }
}