package com.zajko04.virus

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.zajko04.virus.DataBase.AlarmEntity
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil(context: Context) {
    private var notificationManager: NotificationManager? = null
    private var context: Context? = null

    fun sendNotification(context: Context, alarmEntity: AlarmEntity){
        val myNotification = MyNotification(alarmEntity)
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(myNotification.title)
            .setContentText(myNotification.text)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setChannelId(CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification: Notification = mBuilder.build()

        val intent = Intent(context,MyNotificationPublisher::class.java)
        intent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, myNotification.notificationID)
        intent.putExtra(MyNotificationPublisher.NOTIFICATION,notification)
        val pendingIntent = PendingIntent.getBroadcast(context,myNotification.notificationID,intent,PendingIntent.FLAG_CANCEL_CURRENT)

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, myNotification.timeInMilis, pendingIntent)
    }

    fun sendNotifiNOW(context: Context, alarmEntity: AlarmEntity){

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("title")
            .setContentText("TEXT")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true) // makes auto cancel of notification
            .setChannelId(CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH) //set priority of notification

        Log.d("VIRUZ","SEND")
        notificationManager?.notify(0,mBuilder.build())
    }

    @RequiresApi(VERSION_CODES.O)
    fun getChannelID(){
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, "Name", importance)

        channel.description = "desc"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }

    private class MyNotification{
        var title: String
        var text: String
        var timeInMilis: Long
        var notificationID: Int

        @SuppressLint("SimpleDateFormat")
        constructor(alarmEntity: AlarmEntity){
            this.text = alarmEntity.mAlarmContents
            this.title = alarmEntity.mAlarmTitle
            this.notificationID = alarmEntity.mAlarmID
            val date = alarmEntity.mTime.split("-")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR,date[0].toInt())
            calendar.set(Calendar.MONTH,date[1].toInt())
            calendar.set(Calendar.DAY_OF_MONTH,date[2].toInt())
            calendar.set(Calendar.HOUR_OF_DAY,date[3].toInt())
            calendar.set(Calendar.MINUTE,date[4].toInt())
            this.timeInMilis = calendar.timeInMillis
        }
    }

    init {
        this.context = context
        notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            this.getChannelID()
        }
    }

    companion object{
        val CHANNEL_ID = "com.zajko04.virus"
    }
}
