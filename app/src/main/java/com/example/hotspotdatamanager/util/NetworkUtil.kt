package com.example.hotspotdatamanager.util

import java.io.BufferedReader
import java.io.FileReader

object NetworkUtil {

    // Returns a list of Map(ip, mac, mockName)
    fun getConnectedDevices(): List<Map<String, String>> {
        val devices = mutableListOf<Map<String, String>>()
        try {
            val br = BufferedReader(FileReader("/proc/net/arp"))
            var line = br.readLine()
            while (line != null) {
                val splitted = line.split(" +".toRegex()).toTypedArray()
                if (splitted.size >= 4 && splitted[3] != "00:00:00:00:00:00") {
                    val mac = splitted[3]
                    val ip = splitted[0]
                    if (mac.matches(Regex("..:..:..:..:..:.."))) {
                        val mockName = guessDeviceName(mac)
                        devices.add(mapOf("ip" to ip, "mac" to mac, "name" to mockName))
                    }
                }
                line = br.readLine()
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return devices
    }

    private fun guessDeviceName(mac: String): String {
        return when {
            mac.startsWith("00:1A:2B", ignoreCase = true) -> "Galaxy S23"
            mac.startsWith("A1:B2:C3", ignoreCase = true) -> "Windows PC"
            mac.startsWith("00:25:9C", ignoreCase = true) -> "Apple Device"
            mac.startsWith("B4:CE:F6", ignoreCase = true) -> "Android Phone"
            else -> "Unknown Device" // Default naming if vendor not recognized
        }
    }

    fun getTrafficStats(): Pair<Long, Long> {
        return android.net.TrafficStats.getMobileRxBytes() to android.net.TrafficStats.getMobileTxBytes()
    }
}
