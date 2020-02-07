package com.zajko04.virus.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  AlarmEntityDAO {
    //select
    @Query("select * from AlarmsTable order by mTime asc")
    fun getAllAlarms(): List<AlarmEntity>

    //update
    @Query("update AlarmsTable set mAlarmTitle = :mAlarmTitle, mAlarmContents = :mAlarmContents,mTime = :mTime where mAlarmID like :mAlarmID")
    fun updateAlarmByID(mAlarmID: Int, mAlarmTitle: String, mAlarmContents: String, mTime: String)

    //Insert
    @Insert
    fun insertAlarm(mAlarmEntity: AlarmEntity)

    @Insert
    fun insertAllAlarms(alarmsList: List<AlarmEntity>)

    //delete
    @Query("delete from AlarmsTable")
    fun deleteAllAlarms()

    @Query("delete from AlarmsTable where mAlarmID like :mAlarmID")
    fun deleteAlarmByID(mAlarmID: Int)
}