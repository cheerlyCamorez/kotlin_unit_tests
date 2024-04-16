package com.example.unit_tests.presentation.screens.auth

import com.example.unit_tests.domain.useCases.addNewUserUseCase
import com.example.unit_tests.domain.useCases.checkIfUserExistUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlinx.coroutines.test.resetMain
import org.junit.jupiter.api.Assertions.assertFalse


class AuthViewModelTest {
    private val checkIfUserExistUseCase = mock<checkIfUserExistUseCase>()
    private val addNewUserUseCase = mock<addNewUserUseCase>()
    @AfterEach
    fun clean(){
        Mockito.reset(checkIfUserExistUseCase)
        Mockito.reset(addNewUserUseCase)

    }
    @BeforeEach
    fun setup() {

    }

    @Test
    fun `1) checkIfUserExist with empty login should show toast`() {


        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        viewModel.updateLoginField("") // Пустое поле логина
        viewModel.updatePwdField("Testpwd")

        viewModel.checkIfUserExist()

        assertEquals("В поле логина введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
    @Test
    fun `2) checkIfUserExist with empty pwd should show toast`() {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        viewModel.updateLoginField("TestLogin")
        viewModel.updatePwdField("")

        viewModel.checkIfUserExist()

        assertEquals("В поле пароля введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
    @Test
    fun `3) checkIfUserExist with data not exist user should show toast`() = runBlocking {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val testLogin = "testLogin"
        val testPwd = "testPwd"
        viewModel.updateLoginField(testLogin)
        viewModel.updatePwdField(testPwd)
        Mockito.`when`(checkIfUserExistUseCase.invoke(testLogin, testPwd)).thenReturn(false)

        viewModel.checkIfUserExist()

        assertEquals("Такого пользователя не существует", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        assertFalse(viewModel.isUserAutorize.value)
        Dispatchers.resetMain()
    }

    @Test
    fun `4) checkIfUserExist with valid login and password should authorize user`() = runBlocking {


        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        viewModel.updateLoginField("testLogin")
        viewModel.updatePwdField("testPwd")
        Mockito.`when`(checkIfUserExistUseCase.invoke("testLogin", "testPwd")).thenReturn(true)


        viewModel.checkIfUserExist()

        assertTrue(viewModel.isUserAutorize.value)
        Dispatchers.resetMain()

    }
    @Test
    fun `5) addNewUser without data(name) should show toast`() {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val testLogin = "testLogin"
        val testPwd = "testPwd"
        val testUserName = ""
        viewModel.newLoginField(testLogin)
        viewModel.newPwdField(testPwd)
        viewModel.newNameField(testUserName)

        viewModel.addNewUser()

        assertEquals("В поле имени введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
    @Test
    fun `6) addNewUser without data(login) should show toast`() {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val testLogin = ""
        val testPwd = "testPwd"
        val testUserName = "testUserName"
        viewModel.newLoginField(testLogin)
        viewModel.newPwdField(testPwd)
        viewModel.newNameField(testUserName)

        viewModel.addNewUser()

        assertEquals("В поле логина введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
    @Test
    fun `7) addNewUser without data(password) should show toast`() {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val testLogin = "testLogin"
        val testPwd = ""
        val testUserName = "testUserName"
        viewModel.newLoginField(testLogin)
        viewModel.newPwdField(testPwd)
        viewModel.newNameField(testUserName)

        viewModel.addNewUser()

        assertEquals("В поле пароля введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
    @Test
    fun `7) addNewUser valid data(password) should show toast`() = runBlocking {

        Dispatchers.setMain(TestCoroutineDispatcher())
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val testLogin = "testLogin"
        val testPwd = "testPwd"
        val testUserName = "testUserName"
        viewModel.newLoginField(testLogin)
        viewModel.newPwdField(testPwd)
        viewModel.newNameField(testUserName)

        viewModel.addNewUser()

        assertEquals("Новый пользователь добавлен", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        Dispatchers.resetMain()
    }
}