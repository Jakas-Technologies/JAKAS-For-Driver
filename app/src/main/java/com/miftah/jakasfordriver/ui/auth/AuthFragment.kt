package com.miftah.jakasfordriver.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding : FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuthToRegis.setOnClickListener {
            it.findNavController().navigate(R.id.action_authFragment_to_registerFragment)
        }
        binding.btnAuthToLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}