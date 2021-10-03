package com.example.mvvmdemo.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.ProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: ProfileViewModel
    override val kodein by kodein()
    private lateinit var binding: ProfileFragmentBinding

    private val factory: ProfileViewModelFactory by instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}