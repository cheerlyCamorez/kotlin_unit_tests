package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class getUserListUseCaseTest {
    val userRepository = mock<UserRepository>()


    @Test
    fun `1 check user list`() = runBlocking{
        val userList = listOf(
            User(idUser = 1, login = "testUser1", pwd = "testUser1", userName = "testUser1"),
            User(idUser = 2, login = "testUser2", pwd = "testUser2", userName = "testUser2")
        )


        Mockito.`when`(userRepository.getUserList()).thenReturn(flowOf(userList))

        val getUserListUseCase = getUserListUseCase(userRepository)

        val result = mutableListOf<User>()

        getUserListUseCase().collect { userList ->
            result.addAll(userList)
        }

        Assertions.assertEquals(userList, result)
    }
    @Test
    fun `2 check empty list`() = runBlocking {
        Mockito.`when`(userRepository.getUserList()).thenReturn(flowOf(emptyList()))

        val getUserListUseCase = getUserListUseCase(userRepository)

        val result = mutableListOf<User>()

        getUserListUseCase().collect { userList ->
            result.addAll(userList)
        }

        Assertions.assertTrue(result.isEmpty())
    }
}


