package com.metinvandar.cryptotrackerapp.presentation.rate_alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.databinding.FragmentRateAlertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateAlertFragment: Fragment(R.layout.fragment_rate_alert) {

    private var _binding: FragmentRateAlertBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRateAlertBinding.bind(view)
    }
}
