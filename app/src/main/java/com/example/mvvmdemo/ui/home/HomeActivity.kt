package com.example.mvvmdemo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragmentContainerView), binding.drawerLayout)
    }
}