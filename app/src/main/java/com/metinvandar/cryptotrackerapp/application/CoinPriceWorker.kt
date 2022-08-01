package com.metinvandar.cryptotrackerapp.application

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCase
import com.metinvandar.cryptotrackerapp.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class CoinPriceWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val getNotificationCoins: GetNotificationCoinsUseCase
) : CoroutineWorker(appContext, params) {

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(price: Double, coinName: String) {
        val intent = Intent(appContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val channelId = appContext.getString(R.string.default_notification_channel_id)
        val channelName = appContext.getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(appContext, channelId)
            .setColor(ContextCompat.getColor(appContext, R.color.blue_yonder))
            .setContentTitle(appContext.getString(R.string.notification_title))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentText(
                appContext.getString(
                    R.string.notification_message,
                    coinName,
                    price.toString()
                )
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = SystemClock.uptimeMillis().toInt()

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override suspend fun doWork(): Result {
        var success = false
        getNotificationCoins()
            .collectLatest { notificationCoins ->
                success = notificationCoins.isNotEmpty()
                notificationCoins.forEach {
                    showNotification(it.currentPrice, it.coinId)
                }
            }

        return if (success) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}
