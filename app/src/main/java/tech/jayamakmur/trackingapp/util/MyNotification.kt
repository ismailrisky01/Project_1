package tech.jayamakmur.trackingapp.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import tech.jayamakmur.trackingapp.R


class MyNotification(private val context: Context) {
    fun create(adapter: NotificationAdapter) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, adapter.priority)
            notificationChannel.description = CHANNEL_DESC
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_jmd_light)
            .setContentTitle(adapter.title)
            .setContentText(adapter.msg)
        notificationManager!!.notify(adapter.id, notificationBuilder.build())

    }

    class NotificationAdapter(var id: Int, var title: String, var msg: String) {
        var priority = NotificationManager.IMPORTANCE_HIGH
    }

    companion object {
        const val CHANNEL_ID = "Tracking_app_notification"
        const val CHANNEL_NAME = "Tracking App Notification"
        const val CHANNEL_DESC = "TrackingApp Default Notification Channel"

    }
}