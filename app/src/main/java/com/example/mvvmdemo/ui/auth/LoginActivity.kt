package com.example.mvvmdemo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmdemo.databinding.ActivityLoginBinding
import com.example.mvvmdemo.ui.home.HomeActivity
import com.example.mvvmdemo.util.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.net.SocketTimeoutException

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        setContentView(binding.root)

        viewModel.getLoggedInUser().observe(this, { user ->
            if (user != null) {

                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        })

        binding.signInBtn.setOnClickListener {
            loginUser()
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.usernameEdt.text.toString().trim()
        val password = binding.passwordEdt.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            binding.rootLayout.snackbar("E-mail and password areas must be filled!")
            return
        }

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(email, password)
                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)

                } else {
                    binding.rootLayout.snackbar(authResponse.message!!)
                }

            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()

            }

        }
    }
}