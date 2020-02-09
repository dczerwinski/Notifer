package com.zajko04.virus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zajko04.virus.DataBase.AlarmEntity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("VIRUZ","MAIN")
        val notificationUtil = NotificationUtil(this)
        //notificationUtil.sendNotifiNOW(this, AlarmEntity("title","text","2020-02-08 13:30:00"))
        startService(Intent(this,MyService::class.java))
        //finish()
    }
}
