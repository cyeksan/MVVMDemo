package com.example.mvvmdemo.data.repositories

import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.UserDao
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val userDao: UserDao
) : SafeApiRequest(), IUserRepository {

    override suspend fun userLogin(email: String, password: String) : AuthResponse {
        return apiRequest {
            api.userLogin(email, password)
        }
    }

    override suspend fun userSignup(email: String, password: String, name: String) : AuthResponse {
        return apiRequest {
            api.userSignup(email, password, name)
        }
    }

    override suspend fun saveUser(user: User) = userDao.upsert(user)

    override fun getUser() = userDao.getUser()
}