package com.example.mvvmdemo.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.repositories.UserRepository

class ProfileViewModel(userRepository: UserRepository) : ViewModel() {
    val user = userRepository.getUser()
}