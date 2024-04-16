package com.example.unit_tests.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.unit_tests.data.database.entity.user.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TestsDaoTest {
    private lateinit var testsDao: TestsDao
    private lateinit var db: TestsDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, TestsDatabase::class.java
        ).build()
        testsDao = db.testsDao()
    }

    @After

    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun addAndGetUserTest() = runBlocking {
        val user = User(idUser = 1, login = "testUser", pwd = "testPassword", userName = "Test User")

        testsDao.addNewUser(user)

        val retrievedUser = testsDao.getUser(user.login, user.pwd)

        assertEquals(user, retrievedUser)
    }

    @Test
    fun getUserListTest() = runBlocking {
        val userList = listOf(
            User(idUser = 1, login = "user1", pwd = "password1", userName = "User 1"),
            User(idUser = 2, login = "user2", pwd = "password2", userName = "User 2")
        )

        userList.forEach { user ->
            testsDao.addNewUser(user)
        }

        val userListFromDb = testsDao.getUserList().first()

        assertEquals(userList.size, userListFromDb.size)
        assertTrue(userList.containsAll(userListFromDb))
    }
}