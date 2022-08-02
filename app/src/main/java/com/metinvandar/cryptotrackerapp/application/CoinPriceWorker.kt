package com.metinvandar.cryptotrackerapp.application

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.Constants.NOTIFICATION_CHANNEL_ID
import com.metinvandar.cryptotrackerapp.common.Constants.NOTIFICATION_GROUP_KEY
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCase
import com.metinvandar.cryptotrackerapp.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.math.abs

@HiltWorker
class CoinPriceWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val getNotificationCoins: GetNotificationCoinsUseCase
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        var success = false
        getNotificationCoins()
            .collect { notificationCoins ->
                success = notificationCoins.isNotEmpty()
                notificationCoins.forEach {
                    val notification = getNotificationForCoin(it.currentPrice, it.coinId)
                    val notificationManager =
                        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationId = abs((0..999999999999).random().toInt())
                    notificationManager.notify(notificationId, notification)
                }
            }

        return if (success) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun getNotificationForCoin(price: Double, coinName: String): Notification {
        val channelName = appContext.getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
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
            .setGroup(NOTIFICATION_GROUP_KEY)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(getPendingIntent())

        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationBuilder.build()
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(appContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }
    }
}
