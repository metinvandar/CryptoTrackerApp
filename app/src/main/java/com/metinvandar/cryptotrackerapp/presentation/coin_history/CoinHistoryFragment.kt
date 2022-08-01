package com.metinvandar.cryptotrackerapp.presentation.coin_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.databinding.FragmentCoinHistoryBinding
import com.metinvandar.cryptotrackerapp.presentation.adapter.CoinHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinHistoryFragment: Fragment(R.layout.fragment_coin_history) {

    private var _binding: FragmentCoinHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CoinHistoryAdapter

    private val viewModel: CoinHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCoinHistoryBinding.bind(view)
        setToolbar()
        initCoinHistoryList()
        collectUIState()
    }

    private fun collectUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when(uiState) {
                    is HistoryUIState.Result -> {

                    }
                }
            }
        }
    }

    private fun initCoinHistoryList() {
        adapter = CoinHistoryAdapter()
        binding.coinHistoryList.adapter = adapter
    }

    private fun setToolbar() {
        binding.toolbarLayout.materialToolbar.run {
            title = getString(R.string.coin_list_fragment_label)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
