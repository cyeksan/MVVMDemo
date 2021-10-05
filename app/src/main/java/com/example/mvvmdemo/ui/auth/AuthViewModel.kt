package com.example.mvvmdemo.ui.auth


import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.repositories.IUserRepository
import com.example.mvvmdemo.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val userRepository: IUserRepository
) : ViewModel() {

    fun getLoggedInUser() = userRepository.getUser()

    suspend fun userLogin(email: String, password: String) =
        withContext(Dispatchers.IO) {userRepository.userLogin(email, password)}

    suspend fun userSignup(email: String, password: String, name: String) =
        withContext(Dispatchers.IO) {userRepository.userSignup(email, password, name)}

    suspend fun saveLoggedInUser(user: User) = userRepository.saveUser(user)

}