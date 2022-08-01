package com.metinvandar.cryptotrackerapp.presentation

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.metinvandar.cryptotrackerapp.application.CoinPriceWorker
import com.metinvandar.cryptotrackerapp.common.extensions.hideKeyboard
import com.metinvandar.cryptotrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWorker()
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<CoinPriceWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "COIN_RATE_ALERT",
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboard()
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
