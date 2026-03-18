package com.example.hotspotdatamanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hotspotdatamanager.model.AppDatabase
import com.example.hotspotdatamanager.model.Device
import com.example.hotspotdatamanager.util.NetworkUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    val devices: LiveData<List<Device>> = db.deviceDao().getAllDevices()

    private val lastUsageMap = mutableMapOf<String, Long>()

    init {
        startDeviceMonitoring()
    }

    private fun startDeviceMonitoring() {
        viewModelScope.launch {
            while (isActive) {
                val connectedArp = NetworkUtil.getConnectedDevices()

                if (connectedArp.isEmpty()) {
                    // Simulation for demo
                    val mac = "F1:E2:D3:C4:B5:A6"
                    val prevUsage = lastUsageMap[mac] ?: 0L
                    val currentUsage = prevUsage + Random.nextLong(50000, 200000)
                    val speed = (currentUsage - prevUsage) / 5 // bytes per sec approx

                    val mockDevice = Device(
                        macAddress = mac,
                        ipAddress = "192.168.43.99",
                        deviceName = "Demo Emulator Phone",
                        dataUsed = currentUsage,
                        downloadSpeed = speed,
                        uploadSpeed = speed / 4,
                        isBlocked = false,
                        dataLimit = 0
                    )
                    lastUsageMap[mac] = currentUsage
                    db.deviceDao().insert(mockDevice)
                } else {
                    for (dev in connectedArp) {
                        val mac = dev["mac"] ?: continue
                        val ip = dev["ip"] ?: ""
                        val name = dev["name"] ?: "Unknown"

                        val prevUsage = lastUsageMap[mac] ?: 0L
                        // In real environment, we'd fetch actual usage per MAC. 
                        // For this implementation, we simulate increment based on global activity
                        val currentUsage = prevUsage + Random.nextLong(10000, 100000) 
                        val speed = (currentUsage - prevUsage) / 5

                        val newDevice = Device(
                            macAddress = mac,
                            ipAddress = ip,
                            deviceName = name,
                            dataUsed = currentUsage,
                            downloadSpeed = speed,
                            uploadSpeed = speed / 5,
                            isBlocked = false,
                            dataLimit = 0
                        )
                        lastUsageMap[mac] = currentUsage
                        db.deviceDao().insert(newDevice)
                    }
                }
                delay(5000)
            }
        }
    }

    fun updateDeviceLimit(device: Device, newLimit: Long) {
        viewModelScope.launch {
            device.dataLimit = newLimit
            db.deviceDao().update(device)
        }
    }

    fun blockDevice(device: Device) {
        viewModelScope.launch {
            device.isBlocked = true
            db.deviceDao().update(device)
        }
    }
}
