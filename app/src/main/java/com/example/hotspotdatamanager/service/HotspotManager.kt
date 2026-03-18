package com.example.hotspotdatamanager.service

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log

class HotspotManager(private val context: Context) {

    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    fun toggleHotspot(enable: Boolean) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (enable) {
                    wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {}, null)
                } else {
                    // local only hotspot is tricky to disable generically without the reservation object.
                }
            } else {
                val method = wifiManager.javaClass.getDeclaredMethod("setWifiApEnabled", 
                    android.net.wifi.WifiConfiguration::class.java, Boolean::class.java)
                method.invoke(wifiManager, null, enable)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun restartHotspot(callback: () -> Unit) {
        Log.d("HotspotManager", "Restarting Hotspot to clear ARP and refresh devices...")
        // Step 1: Turn off
        toggleHotspot(false)

        // Step 2: Wait roughly 2 seconds to allow network stack to drop clients
        Handler(Looper.getMainLooper()).postDelayed({
            // Step 3: Turn back on
            toggleHotspot(true)
            Log.d("HotspotManager", "Hotspot restarted.")
            callback()
        }, 2000)
    }
}
