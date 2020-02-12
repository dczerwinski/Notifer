package com.zajko04.virus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.zajko04.virus.DataBase.AlarmEntity
import com.zajko04.virus.DataBase.AlarmEntityDatabase
import kotlinx.coroutines.GlobalScope

class DeviceBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()
        Log.d("VIRUZ","AFTER BOOT")

        Thread{
            val alarmEntityDatabase = AlarmEntityDatabase.getDatabase(context,
                GlobalScope
            )
            var alarmsList:List<AlarmEntity> = alarmEntityDatabase.alarmDao().getAllAlarms()
            val notificationUtil = NotificationUtil(context)
            while(alarmsList.isEmpty()){
                alarmsList = alarmEntityDatabase.alarmDao().getAllAlarms()
                Log.d("VIRUZ","THREAD START ${alarmsList.size}")
            }
            for(alarm in alarmsList){
                Log.d("VIRUZ",alarm.mTime)
                notificationUtil.sendNotification(context,alarm)
            }
        }.start()
    }
}