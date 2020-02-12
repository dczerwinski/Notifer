package com.zajko04.virus

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.zajko04.virus.DataBase.AlarmEntityDatabase
import kotlinx.coroutines.GlobalScope

class MyNotificationPublisher: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION)
        val notificationID: Int = intent.getIntExtra(NOTIFICATION_ID,0)
        Log.d("VIRUZ", " - MyNotificationPublisher  onReceive")
        notificationManager.notify(notificationID,notification)

        Thread{
            val alarmEntityDatabase = AlarmEntityDatabase.getDatabase(context,GlobalScope)
            alarmEntityDatabase.alarmDao().deleteAlarmByID(notificationID)
        }.start()
    }

    companion object{
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION = "notification"
    }
}