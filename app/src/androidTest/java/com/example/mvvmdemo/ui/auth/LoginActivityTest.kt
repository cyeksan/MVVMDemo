package com.example.mvvmdemo.ui.auth

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvmdemo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

    }

    @Test
    fun login() {

        onView(withId(R.id.username_edt)).perform(ViewActions.typeText("Cansu@gmail.com"))
        onView(withId(R.id.password_edt)).perform(ViewActions.typeText("123"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.sign_in_btn)).perform(click())

    }
}