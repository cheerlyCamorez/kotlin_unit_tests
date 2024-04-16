package com.example.unit_tests.domain.repository.user

import com.example.unit_tests.data.database.TestsDao
import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.kotlin.mock


class UserRepositoryImplTest {
    private val testsDao = mock<TestsDao>()

    @Test
    fun `1 check user if exists`() = runBlocking {
        val userRepository = UserRepositoryImpl(testsDao)
        val login = "testUser"
        val pwd = "testPwd"
        Mockito.`when`(testsDao.getUser(login, pwd)).thenReturn(User(idUser = 1, login = login, pwd = pwd, userName = "testUser"))

        val result = userRepository.checkIfUserExist(login, pwd)

        assertTrue(result)
    }
    @Test
    fun `2 check add user`() = runBlocking {

        val userRepository = UserRepositoryImpl(testsDao)
        val user = User(idUser = 1, login = "testUser", pwd = "testPwd", userName = "testUser")

        userRepository.addNewUser(user)

        Mockito.verify(testsDao).addNewUser(user)
    }
    @Test
    fun `3 get user list`() = runBlocking {
        val userList = listOf(
            User(idUser = 1, login = "testUser1", pwd = "testPwd1", userName = "testUser1"),
            User(idUser = 2, login = "testUser2", pwd = "testPwd2", userName = "testUser2")
        )
        val userListFlow = flow { emit(userList) }

        Mockito.`when`(testsDao.getUserList()).thenReturn(userListFlow)

        val userRepository = UserRepositoryImpl(testsDao)

        val resultUserList = userRepository.getUserList().toList()

        assertEquals(userList, resultUserList)
    }

}