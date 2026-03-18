package com.example.hotspotdatamanager.service

import android.content.Context
import android.util.Log
import android.widget.Toast

object ControlUtil {
    fun blockDevice(context: Context, macAddress: String) {
        val msg = "Blocking "\$macAddress" requested. Note: root or system privileges required for actual IP ban."
        Log.w("ControlUtil", msg)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
