package com.example.hotspotdatamanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usage_logs")
data class UsageLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val macAddress: String,
    val timestamp: Long,
    val dataAmount: Long
)
