package com.example.mvvmdemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.test.core.app.ApplicationProvider
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.NetworkConnectionInterceptor
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.network.responses.AuthResponse
import com.example.mvvmdemo.data.repositories.IUserRepository

class FakeUserRepository() : SafeApiRequest(), IUserRepository {
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()
    private val api = MyApi(NetworkConnectionInterceptor(context))

    override suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            api.userLogin(email, password)
        }
    }

    override suspend fun userSignup(email: String, password: String, name: String): AuthResponse {
        val api = MyApi(NetworkConnectionInterceptor(context))
        return apiRequest {
            api.userSignup(email, password, name)
        }
    }

    override suspend fun saveUser(user: User): Long = AppDatabase.invoke(context).getUserDao().upsert(user)

    override fun getUser(): LiveData<User> = AppDatabase.invoke(context).getUserDao().getUser()
}