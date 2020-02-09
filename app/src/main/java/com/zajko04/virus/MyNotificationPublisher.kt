package com.zajko04.virus

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyNotificationPublisher: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION)
        val notificationID: Int = intent.getIntExtra(NOTIFICATION_ID,0)
        notificationManager.notify(notificationID,notification)
    }

    companion object{
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION = "notification"
    }
}