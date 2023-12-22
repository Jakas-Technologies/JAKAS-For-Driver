package com.miftah.jakasfordriver.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.miftah.jakasfordriver.databinding.FragmentQrCodeBinding
import com.miftah.jakasfordriver.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QrCodeFragment : Fragment() {

    private var _binding: FragmentQrCodeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by activityViewModels()

    private var qrString: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgRefresh.setOnClickListener {
            if (qrString == null) {
                viewModel.getQrCode().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Error -> {

                        }
                        Result.Loading -> {

                        }
                        is Result.Success -> {
                            qrString = result.data.qrCode
                            Glide.with(this)
                                .load(qrString)
                                .into(binding.tvImageQr)
                            binding.imgRefresh.visibility = View.GONE
                        }
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