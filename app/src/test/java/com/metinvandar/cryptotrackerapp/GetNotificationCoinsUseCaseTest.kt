package com.metinvandar.cryptotrackerapp

import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponse
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNotificationCoinsUseCaseTest {

    @MockK
    private lateinit var repository: CryptoRepository

    private lateinit var getNotificationCoins: GetNotificationCoinsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getNotificationCoins = GetNotificationCoinsUseCaseImpl(repository)
    }

    @Test
    fun `when there are alert coins expect notification coins`() = runTest {

        coEvery { repository.getAlertCoins() } returns coinAlertEntities

        coEvery {
            repository.getSimplePrice(any())
        } returns flow {
            emit(Resource.Success(simplePrices))
        }

        coEvery { repository.saveCoinHistory(any()) } returns Unit
        getNotificationCoins().collect {
            assert(it.size == coinAlertEntities.size)
            for (i in it.indices) {
                val simplePrice = it[i]
                val alertEntity = coinAlertEntities[i]
                assert(simplePrice.coinId == alertEntity.id)
            }
        }
        coVerify { repository.getAlertCoins() }
        coVerify(exactly = coinAlertEntities.size) { repository.saveCoinHistory(any()) }
    }

    @Test
    fun `when simplePrice error expect empty notification coins`() = runTest {
        coEvery { repository.getAlertCoins() } returns coinAlertEntities
        coEvery { repository.getSimplePrice(any()) } returns flow {
            Resource.Error(ErrorType.UNKNOWN_ERROR)
        }
        getNotificationCoins().collect {
            assert(it.isEmpty())
            coVerify(exactly = 0) { repository.getSimplePrice(any()) }
            coVerify(exactly = 0) { repository.saveCoinHistory(any()) }
        }
    }

    @Test
    fun `when no saved coin expect empty notification coins`() = runTest {
        coEvery { repository.getAlertCoins() } returns emptyList()
        getNotificationCoins().collect {
            assert(it.isEmpty())
            coVerify(exactly = 0) { repository.getSimplePrice(any()) }
            coVerify(exactly = 0) { repository.saveCoinHistory(any()) }
        }
    }

    private val coinAlertEntities = listOf(
        CoinAlertEntity(
            id = "bitcoin",
            name = "btc",
            price = 10.0,
            minValue = 5.0,
            maxValue = 10.0
        ),
        CoinAlertEntity(
            id = "ripple",
            name = "xrp",
            price = 3.0,
            minValue = 2.0,
            maxValue = 5.0
        )
    )

    private val simplePrices = PriceResponseWrapper(
        coinMap = mapOf(
            "bitcoin" to PriceResponse(price = 1.0),
            "ripple" to PriceResponse(price = 6.0)
        )
    )
}
