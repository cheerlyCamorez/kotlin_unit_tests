package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.mock

class addNewUserUseCaseTest {



    private val userRepository = mock<UserRepository>()
    @Test
    fun `1) add user with valid data`() = runBlocking {
        val user = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")

        addNewUserUseCase(userRepository).invoke(user)

        Mockito.verify(userRepository, Mockito.times(1)).addNewUser(User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1"))
    }
    @Test
    fun `2) add 2 users with same data`() = runBlocking {
        val user1 = User(idUser = 1, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")
        val user2 = User(idUser = 2, login = "testUser1", pwd = "testPassword1", userName = "Test User 1")

        addNewUserUseCase(userRepository).invoke(user1)

        Mockito.verify(userRepository, Mockito.times(1)).addNewUser(user1)

        val thrown = assertThrows<RuntimeException> {
            addNewUserUseCase(userRepository).invoke(user2)
        }
        Assertions.assertEquals("User already exists", thrown.message)
    }
    @Test
    fun `3) add user with invalid data (without password)`() = runBlocking{
        val user1 = User(idUser = 1, login = "testUser1", pwd = "", userName = "Test User 1")

        addNewUserUseCase(userRepository).invoke(user1)

        val exception = RuntimeException("Invalid user data")
        doThrow(exception).`when`(userRepository).addNewUser(user1)

        val thrown = assertThrows<RuntimeException> {
            addNewUserUseCase(userRepository).invoke(user1)
        }
        Assertions.assertEquals(exception, thrown)

    }
    @Test
    fun `4) add user with invalid data (without login)`() = runBlocking{
        val user1 = User(idUser = 1, login = "", pwd = "Test User 1", userName = "Test User 1")

        addNewUserUseCase(userRepository).invoke(user1)

        val exception = RuntimeException("Invalid user data")
        doThrow(exception).`when`(userRepository).addNewUser(user1)

        val thrown = assertThrows<RuntimeException> {
            addNewUserUseCase(userRepository).invoke(user1)
        }
        Assertions.assertEquals(exception, thrown)

    }
    @Test
    fun `5) add user with invalid data (without userName)`() = runBlocking{
        val user1 = User(idUser = 1, login = "Test User 1", pwd = "Test User 1", userName = "")

        addNewUserUseCase(userRepository).invoke(user1)

        val exception = RuntimeException("Invalid user data")
        doThrow(exception).`when`(userRepository).addNewUser(user1)

        // Попытка добавления пользователя с неверными данными
        val thrown = assertThrows<RuntimeException> {
            addNewUserUseCase(userRepository).invoke(user1)
        }
        Assertions.assertEquals(exception, thrown)

    }

}



