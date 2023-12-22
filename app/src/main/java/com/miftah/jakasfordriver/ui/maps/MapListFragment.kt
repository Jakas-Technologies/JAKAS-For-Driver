package com.miftah.jakasfordriver.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftah.jakasfordriver.databinding.FragmentMapListBinding
import com.miftah.jakasfordriver.utils.Result
import com.miftah.jakasfordriver.utils.toPassengerList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapListFragment : Fragment() {

    private var _binding: FragmentMapListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by activityViewModels()

    private val adapter = MidtransAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.transaction.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Error -> TODO()
                Result.Loading -> TODO()
                is Result.Success -> TODO()
            }
        }

        binding.refreshData.setOnClickListener {
            viewModel.getMidtransUpdate().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }

                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvTotalPrice.text = result.data.totalFare
                        adapter.submitList(result.data.toPassengerList())
                    }
                }
            }
        }
        binding.rvTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransaction.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}