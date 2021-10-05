package com.example.mvvmdemo.ui.auth

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.responses.AuthResponse
import com.example.mvvmdemo.repository.FakeUserRepository
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class AuthViewModelTest : TestCase() {

    private lateinit var viewModel: AuthViewModel

    @Before
    public override fun setUp() {
        super.setUp()

        viewModel = AuthViewModel(FakeUserRepository())

    }

    @DelicateCoroutinesApi
    @Test
    fun testLoginWithWrongAccount() {
        var ex: Exception? = null

        runBlocking {
            try {
                viewModel.userLogin("cansuaktas@eskisehir.edu.tr", "1234")
            } catch (e: Exception) {
                ex = e
            }        }
        assertThat(ex?.message).isEqualTo("Invalid e-mail or password\nError Code: 401")
    }


    @DelicateCoroutinesApi
    @Test
    fun testLoginWithTrueAccount() {
        var authResponse: AuthResponse?
        runBlocking {
            authResponse = viewModel.userLogin("cansu.aktas@huawei.com", "123456")
        }
        assertThat(authResponse?.isSuccess).isEqualTo(true)

    }

    @DelicateCoroutinesApi
    @Test
    fun testSignupWithExistingAccount() {
        var ex: Exception? = null

        runBlocking {
            try {
                viewModel.userSignup(
                    "cansu.aktas@huawei.com",
                    "12345678",
                    "Cansu"
                )
            } catch (e: Exception) {
                ex = e
            }        }
        assertThat(ex?.message).isEqualTo("The e-mail has already been taken\nError Code: 400")

    }

    @DelicateCoroutinesApi
    @Test
    fun testAddAndGetUser() {

        var uid : Long?
        val user = User(
            5,
            "Cansu Yeksan Aktas",
            "cansuaktas@gmail.com",
            "1234"
        )
        runBlocking {
            uid = viewModel.saveLoggedInUser(user)
        }


        assertThat(uid).isEqualTo(0)
    }

}