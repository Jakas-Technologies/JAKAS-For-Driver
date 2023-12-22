package com.miftah.jakasfordriver.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.databinding.FragmentLoginBinding
import com.miftah.jakasfordriver.ui.home.MainActivity
import com.miftah.jakasfordriver.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.editText?.text.toString()
            val pass = binding.edLoginPassword.editText?.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.userLogin(email, pass).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Sukses", Toast.LENGTH_SHORT
                            ).show()
                            viewModel.createSave(result.data.user.id, result.data.accessToken)
                            Intent(requireActivity(), MainActivity::class.java).let {
                                startActivity(it)
                            }
                        }
                    }
                }
            }
        }
        binding.tvGoToRegis.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}