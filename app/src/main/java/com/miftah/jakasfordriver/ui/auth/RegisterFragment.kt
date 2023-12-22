package com.miftah.jakasfordriver.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.miftah.jakasfordriver.databinding.FragmentRegisterBinding
import com.miftah.jakasfordriver.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegis.setOnClickListener {
            val username = binding.edRegisUsername.text.toString()
            val email = binding.edRegisEmail.text.toString()
            val password = binding.edRegisPassword.text.toString()
            val age = binding.edRegisAge.text.toString().toInt()
            val routeName = binding.edRegisDirection.text.toString()
            val licensePlate = binding.edRegisLicense.text.toString()
            viewModel.userRegis(username = username, email = email, password = password, age = age, licensePlate = licensePlate, routeName = routeName)
                .observe(viewLifecycleOwner) { data ->
                    when (data) {
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
                            findNavController().popBackStack()
                        }
                    }
                }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}