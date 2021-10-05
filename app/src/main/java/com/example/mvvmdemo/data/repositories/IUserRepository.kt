package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.responses.AuthResponse

interface IUserRepository {
    suspend fun userLogin(email: String, password: String) : AuthResponse

    suspend fun userSignup(email: String, password: String, name: String) : AuthResponse

    suspend fun saveUser(user: User) : Long

    fun getUser() : LiveData<User>
}