package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class checkIfUserExistUseCaseTest {
    val userRepository = mock<UserRepository>()
    @Test
    fun `1 check created user`() = runBlocking {
        val user = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")

        addNewUserUseCase(userRepository).invoke(user)

        Mockito.`when`(userRepository.checkIfUserExist(user.login, user.pwd)).thenReturn(true)

        val actual = checkIfUserExistUseCase(userRepository).invoke(user.login, user.pwd)

        val expected = true

        Assertions.assertEquals(expected, actual)

   }
    @Test
    fun `2 check not existed user`() = runBlocking {
        val user = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")

        Mockito.`when`(userRepository.checkIfUserExist(user.login, user.pwd)).thenReturn(false)

        val actual = checkIfUserExistUseCase(userRepository).invoke(user.login, user.pwd)

        val expected = false

        Assertions.assertEquals(expected, actual)
    }
    @Test
    fun `3 check existed user, but put invalid login`() = runBlocking {
        val user = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")
        addNewUserUseCase(userRepository).invoke(user)
        Mockito.`when`(userRepository.checkIfUserExist("testUser", user.pwd)).thenReturn(false)

        val actual = checkIfUserExistUseCase(userRepository).invoke("testUser", user.pwd)

        val expected = false

        Assertions.assertEquals(expected, actual)
    }
    @Test
    fun `4 check existed user, but put invalid password`() = runBlocking {
        val user = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")
        addNewUserUseCase(userRepository).invoke(user)
        Mockito.`when`(userRepository.checkIfUserExist(user.login, "testWrongPass")).thenReturn(false)

        val actual = checkIfUserExistUseCase(userRepository).invoke(user.login, "testWrongPass")

        val expected = false

        Assertions.assertEquals(expected, actual)
    }
}