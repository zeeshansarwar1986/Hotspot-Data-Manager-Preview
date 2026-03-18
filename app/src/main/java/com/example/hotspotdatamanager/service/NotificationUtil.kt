package com.example.hotspotdatamanager.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.hotspotdatamanager.R

/**
 * Description: Pushes alerts for data thresholds.
 * Tools used: NotificationManager.
 * To edit: Customize channel in createNotificationChannel().
 */
object NotificationUtil {
    fun sendUsageAlert(context: Context, deviceName: String, dataUsedMsg: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "usage_alerts"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Usage Alerts", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("High Data Usage Alert")
            .setContentText(deviceName + " has used " + dataUsedMsg + ".")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(deviceName.hashCode(), notification)
    }
}
