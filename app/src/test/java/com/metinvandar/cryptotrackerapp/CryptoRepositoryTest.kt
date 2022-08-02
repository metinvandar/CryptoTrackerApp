package com.metinvandar.cryptotrackerapp

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity
import com.metinvandar.cryptotrackerapp.data.remote.api.CryptoApi
import com.metinvandar.cryptotrackerapp.data.remote.models.CoinResponse
import com.metinvandar.cryptotrackerapp.data.remote.repository.CryptoRepositoryImpl
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class CryptoRepositoryTest {

    @MockK
    private lateinit var cryptoApi: CryptoApi

    @MockK
    private lateinit var historyDao: CoinHistoryDao

    @MockK
    private lateinit var alertDao: CoinAlertDao

    private lateinit var repository: CryptoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = CryptoRepositoryImpl(
            coinAlertDao = alertDao,
            coinHistoryDao = historyDao,
            cryptoApi = cryptoApi
        )
    }

    @Test
    fun `get initial coins success`() = runTest {
        coEvery { cryptoApi.coins() } returns initialCoinResponse

        val list = repository.getCoins().toList()
        assert(list[0] is Resource.Loading)
        assert(list[1] is Resource.Success)
        assert(list[2] is Resource.Loading)

        assert((list[1] as Resource.Success).data.size == initialCoinResponse.size)
    }

    @Test
    fun `get initial coins fail`() = runTest {
        coEvery { cryptoApi.coins() } throws  RuntimeException("error description")

        val list = repository.getCoins().toList()
        assert(list[0] is Resource.Loading)
        assert(list[1] is Resource.Error)
        assert(list[2] is Resource.Loading)

        assert((list[1] as Resource.Error).error == ErrorType.UNKNOWN_ERROR)
    }

    @Test
    fun `get history coins sorted`() = runTest {
        val coinId = "12"
        coEvery { historyDao.getCoinHistory(coinId) } returns historyList

        repository.getCoinHistory(coinId).collect { historyList ->
            assert(historyList.size == historyList.size)
            for (i in historyList.indices) {
                if (i != historyList.size - 1) {
                    val currentHistory = historyList[i]
                    val nextHistory = historyList[i + 1]
                    assert(currentHistory.recordTime >= nextHistory.recordTime)
                }
            }
        }
        coVerify { historyDao.getCoinHistory(withArg { assert(it == coinId) }) }
    }

    private val historyList = listOf(
        CoinHistoryEntity(coinId = "bitcoin", price = 0.0, recordTime = 124),
        CoinHistoryEntity(coinId = "ripple", price = 0.0, recordTime = 85),
        CoinHistoryEntity(coinId = "ethereum", price = 0.0, recordTime = 354)
    )


    private val initialCoinResponse = listOf(
        CoinResponse(
            id = "bitcoin",
            name = "btc",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0,
        ),
        CoinResponse(
            id = "ripple",
            name = "xrp",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0
        ),
        CoinResponse(
            id = "ethereum",
            name = "eth",
            symbol = "",
            image = "",
            currentPrice = 0.0,
            priceChangePercentage = 0.0
        )
    )

}
