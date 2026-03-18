package com.example.hotspotdatamanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "app_usage",
    foreignKeys = [
        ForeignKey(
            entity = Device::class,
            parentColumns = ["macAddress"],
            childColumns = ["macAddress"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AppUsage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val macAddress: String,
    val appName: String,
    val packageName: String,
    var dataUsed: Long,
    var iconUrl: String? = null
)
