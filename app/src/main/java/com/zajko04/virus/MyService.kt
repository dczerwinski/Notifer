package com.zajko04.virus

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.zajko04.virus.DataBase.AlarmEntity
import com.zajko04.virus.DataBase.AlarmEntityDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("VIRUZ","onStartCommand")
        GlobalScope.launch {
            val alarmEntityDatabase = AlarmEntityDatabase.getDatabase(applicationContext,GlobalScope )
            val alarmsList:List<AlarmEntity> = alarmEntityDatabase.alarmDao().getAllAlarms()
            val notificationUtil = NotificationUtil(applicationContext)
            for(alarm in alarmsList){
                Log.d("VIRUZ",alarm.mTime)
                notificationUtil.sendNotification(applicationContext,alarm)
               //notificationUtil.sendNotifiNOW(applicationContext,alarm)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}