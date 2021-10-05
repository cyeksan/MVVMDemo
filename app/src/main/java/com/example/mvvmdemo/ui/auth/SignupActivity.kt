package com.example.mvvmdemo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmdemo.databinding.ActivitySignupBinding
import com.example.mvvmdemo.ui.home.HomeActivity
import com.example.mvvmdemo.util.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignupActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        setContentView(binding.root)

        viewModel.getLoggedInUser().observe(this, { user ->
            if(user != null) {

                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        })

        binding.signupBtn.setOnClickListener {
            userSignup()
        }

        binding.signInBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun userSignup() {
        val name = binding.usernameEdt.text.toString().trim()
        val email = binding.usermailEdt.text.toString().trim()
        val password = binding.passwordEdt.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEdt.text.toString().trim()

        if (name.isEmpty()) {
            binding.rootLayout.snackbar("Name is required!")
            return
        }
        if (email.isEmpty()) {
            binding.rootLayout.snackbar("E-mail is required!")
            return
        }
        if (password.isEmpty()) {
            binding.rootLayout.snackbar("Please enter a password!")
            return
        }
        if (password != confirmPassword) {
            binding.rootLayout.snackbar("Passwords did not match!")
            return
        }

        lifecycleScope.launch {

            try {
                val authResponse = viewModel.userSignup(email, password, name)
                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)

                } else {
                    binding.rootLayout.snackbar(authResponse.message!!)
                }

            } catch (e: ApiException) {
                binding.rootLayout.snackbar(e.message!!)
            } catch (e: NoInternetException) {
                binding.rootLayout.snackbar(e.message!!)
            } catch (e: SocketTimeoutException) {
                binding.rootLayout.snackbar(e.message!!)
            } catch (e: ConnectException){
                binding.rootLayout.snackbar(e.message!!)
            }
        }
    }

}