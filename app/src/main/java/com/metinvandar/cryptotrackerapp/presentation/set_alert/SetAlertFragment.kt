package com.metinvandar.cryptotrackerapp.presentation.set_alert

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.extensions.snackBar
import com.metinvandar.cryptotrackerapp.common.extensions.visible
import com.metinvandar.cryptotrackerapp.databinding.FragmentRateAlertBinding
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class SetAlertFragment : Fragment(R.layout.fragment_rate_alert) {

    private var _binding: FragmentRateAlertBinding? = null
    private val binding get() = _binding!!
    private lateinit var coin: CoinDomainModel

    private val viewModel: SetAlertViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRateAlertBinding.bind(view)
        coin = arguments?.getParcelable("coin")!!
        binding.toolbarLayout.materialToolbar.title = coin.name
        binding.toolbarLayout.materialToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setCoinInfo(coin)
        collectInputState()

        binding.setRateButton.setOnClickListener {
            viewModel.validateInputs(
                minRate = binding.minRateEditText.text.toString(),
                maxRate = binding.maxRateEditText.text.toString()
            )
        }
    }

    private fun collectInputState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.inputState.collectLatest { state ->
                when (state) {
                    is InputState.MinRateError -> {
                        binding.run {
                            minRateTextInputLayout.error = state.errorMessage
                            maxRateTextInputLayout.error = null
                        }
                    }
                    is InputState.MaxRateError -> {
                        binding.run {
                            maxRateTextInputLayout.error = state.errorMessage
                            minRateTextInputLayout.error = null
                        }
                    }
                    is InputState.AllInputsError -> {
                        binding.run {
                            maxRateTextInputLayout.error = state.errorMessage
                            minRateTextInputLayout.error = state.errorMessage
                        }
                    }

                    is InputState.Valid -> {
                        binding.run {
                            minRateTextInputLayout.error = null
                            maxRateTextInputLayout.error = null
                        }
                        saveMainAndMaxRate()
                    } else -> {}
                }
            }
        }
    }

    private fun saveMainAndMaxRate() {
        val minRate = binding.minRateEditText.text.toString().toDouble()
        val maxRate = binding.maxRateEditText.text.toString().toDouble()
        viewModel.saveCoinAlert(coin, minRate, maxRate)
        requireView().snackBar(
            message = getString(R.string.coin_rate_value_successfully_saved, coin.name),
            actionButtonText = getString(R.string.go_back),
            action = {
                findNavController().navigateUp()
            }
        )
    }

    private fun setCoinInfo(coin: CoinDomainModel) {
        binding.run {
            coinHeaderLayout.coinPrice.text =
                getString(
                    R.string.value_with_currency,
                    coin.currentPrice.toString()
                )
            if (coin.priceChangePercentage > 0) {
                coinHeaderLayout.coinPriceChange.text = getString(
                    R.string.value_with_percentage,
                    coin.priceChangePercentage.toString()
                )
                coinHeaderLayout.priceChangeArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.vc_arrow_upward
                    )
                )
            } else if (coin.priceChangePercentage < 0) {
                val priceChange = -abs(coin.priceChangePercentage)
                coinHeaderLayout.coinPriceChange.text =
                    getString(R.string.value_with_percentage, priceChange.toString())
                coinHeaderLayout.priceChangeArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.vc_arrow_downward
                    )
                )
            } else {
                coinHeaderLayout.priceChangeArrow.visible = false
            }
            coinHeaderLayout.coin = coin
            executePendingBindings()
        }
    }
}
