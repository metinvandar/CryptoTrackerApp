package com.metinvandar.cryptotrackerapp.presentation.coin_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.extensions.handleError
import com.metinvandar.cryptotrackerapp.common.extensions.visible
import com.metinvandar.cryptotrackerapp.databinding.FragmentCoinListBinding
import com.metinvandar.cryptotrackerapp.presentation.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list) {
    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private lateinit var coinListAdapter: CoinListAdapter

    private val viewModel: CoinListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCoinListBinding.bind(view)
        setToolbar()
        getCoins()
        initCoinList()
        collectUIState()
    }

    private fun initCoinList() {
        coinListAdapter = CoinListAdapter().apply {
            onItemClick = {
                val direction = CoinListFragmentDirections.actionCoinListToRateAlert(it)
                findNavController().navigate(direction)
            }
        }
        binding.coinList.adapter = coinListAdapter
    }

    private fun getCoins() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getInitialCoins()
            }
        }
    }

    private fun collectUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.coinListState.collectLatest { state ->
                when (state) {
                    is CoinListUIState.Loading -> {
                        binding.progressBar.visible = state.isLoading
                    }
                    is CoinListUIState.Result -> {
                        coinListAdapter.submitList(state.coins)
                    }
                    is CoinListUIState.Error -> handleError(
                        errorState = state,
                        retry = {
                            viewModel.getInitialCoins()
                        }
                    )
                }
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarLayout.materialToolbar.run {
            title = getString(R.string.coin_list_fragment_label)
            navigationIcon = null
        }
    }
}
