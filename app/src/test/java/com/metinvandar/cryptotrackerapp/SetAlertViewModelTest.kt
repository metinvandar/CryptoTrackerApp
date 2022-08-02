package com.metinvandar.cryptotrackerapp

import app.cash.turbine.testIn
import com.metinvandar.cryptotrackerapp.common.ResourceManager
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.usecase.SaveCoinAlertUseCase
import com.metinvandar.cryptotrackerapp.presentation.set_alert.InputState
import com.metinvandar.cryptotrackerapp.presentation.set_alert.SetAlertViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class SetAlertViewModelTest {

    @MockK
    private lateinit var saveCoinAlert: SaveCoinAlertUseCase

    @MockK
    private lateinit var resourceManager: ResourceManager

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val viewModel by lazy {
        SetAlertViewModel(saveCoinAlert, resourceManager)
    }

    @Before
    fun setUo() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `save coin alert`() {
        val coinToSave = CoinDomainModel(
            id = "bitcoin",
            name = "btc",
            minRate = 10.0,
            maxRate = 20.0,
            image = "",
            symbol = "",
            currentPrice = 12.0,
            priceChangePercentage = 20.0
        )
        coEvery { saveCoinAlert(coinToSave) } returns Unit

        viewModel.saveCoinAlert(coinToSave)
        coVerify { saveCoinAlert.invoke(withArg {
            assert(it.id == coinToSave.id)
        }) }
    }

    @Test
    fun `when input is invalid state must be error state`() = runTest {
        val minRateErrorMessage = "min rate error"
        val maxRateErrorMessage = "max rate error"
        val allInputsErrorMessage = "all inputs error"

        every { resourceManager.getString(R.string.min_rate_input_empty_error) } returns minRateErrorMessage
        every { resourceManager.getString(R.string.max_rate_input_empty_error) } returns maxRateErrorMessage
        every { resourceManager.getString(R.string.max_rate_must_be_greater) } returns allInputsErrorMessage

        val state = viewModel.inputState
        val turbine = state.testIn(this)
        assert(turbine.awaitItem() == InputState.Initial)

        viewModel.validateInputs(minRate = "", maxRate = "10")
        assert(turbine.awaitItem() == InputState.MinRateError(minRateErrorMessage))

        viewModel.validateInputs("24", null)
        assert(turbine.awaitItem() == InputState.MaxRateError(maxRateErrorMessage))

        viewModel.validateInputs(minRate = "47", maxRate = "36")
        assert(turbine.awaitItem() == InputState.AllInputsError(allInputsErrorMessage))
        turbine.cancel()
    }
}
