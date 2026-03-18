package com.example.hotspotdatamanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a connected device.
 */
@Entity(tableName = "devices")
data class Device(
    @PrimaryKey val macAddress: String,
    val ipAddress: String,
    val deviceName: String,
    var dataUsed: Long,
    var downloadSpeed: Long = 0, // current speed in bytes/sec
    var uploadSpeed: Long = 0,   // current speed in bytes/sec
    var isBlocked: Boolean,
    var dataLimit: Long
)
