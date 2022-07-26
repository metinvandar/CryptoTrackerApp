package com.metinvandar.cryptotrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.domain.usecase.GetSimplePriceUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoins: GetCoinsUseCase
): ViewModel() {

}
