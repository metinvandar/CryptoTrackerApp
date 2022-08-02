package com.metinvandar.cryptotrackerapp

import app.cash.turbine.testIn
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCaseImpl
import com.metinvandar.cryptotrackerapp.presentation.coin_list.CoinListUIState
import com.metinvandar.cryptotrackerapp.presentation.coin_list.CoinListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class CoinListViewModelTest {

    @MockK
    private lateinit var getCoins: GetCoinsUseCase

    @MockK
    private lateinit var repository: CryptoRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val viewModel by lazy {
        CoinListViewModel(getCoins)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getCoins = GetCoinsUseCaseImpl(repository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
    @Test
    fun `get initial coins success`() = runTest  {
        coEvery { getCoins() } returns flow {
            emit(Resource.Success(initialCoinList))
        }
        val state = viewModel.coinListState
        val turbine = state.testIn(this)
        assert(CoinListUIState.Loading(true) == turbine.awaitItem())
        viewModel.getInitialCoins()
        assert(CoinListUIState.Result(initialCoinList) == turbine.awaitItem())
        turbine.cancel()
    }

    @Test
    fun `get initial coins fail`() = runTest  {
        coEvery { getCoins() } returns flow {
            emit(Resource.Error(ErrorType.UNKNOWN_ERROR))
        }
        val state = viewModel.coinListState
        val turbine = state.testIn(this)
        assert(CoinListUIState.Loading(true) == turbine.awaitItem())
        viewModel.getInitialCoins()
        assert(CoinListUIState.Error(ErrorType.UNKNOWN_ERROR) == turbine.awaitItem())
        turbine.cancel()
    }

    private val initialCoinList = listOf(
        CoinDomainModel(
            id = "bitcoin",
            name = "btc",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0
        ),
        CoinDomainModel(
            id = "ripple",
            name = "xrp",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0
        ),
        CoinDomainModel(
            id = "ethereum",
            name = "eth",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0
        )
    )
}
