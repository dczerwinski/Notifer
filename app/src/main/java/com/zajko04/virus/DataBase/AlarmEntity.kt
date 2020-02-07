package com.zajko04.virus.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlarmsTable")
data class AlarmEntity(
    @ColumnInfo(name = "mAlarmTitle") var mAlarmTitle: String,
    @ColumnInfo(name = "mAlarmContents") var mAlarmContents: String,
    @ColumnInfo(name = "mTime") var mTime: String
) {
    @PrimaryKey(autoGenerate = true)
    var mAlarmID: Int = 0
}