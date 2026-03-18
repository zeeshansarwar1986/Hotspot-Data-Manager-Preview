package com.example.hotspotdatamanager.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspotdatamanager.R
import com.example.hotspotdatamanager.model.Device
import com.google.android.material.button.MaterialButton

class DeviceAdapter(
    private var devices: List<Device>,
    private val onBlockClick: (Device) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDeviceName: TextView = view.findViewById(R.id.tvDeviceName)
        val tvIpMac: TextView = view.findViewById(R.id.tvIpMac)
        val tvDataUsed: TextView = view.findViewById(R.id.tvDataUsed)
        val tvDownloadSpeed: TextView = view.findViewById(R.id.tvDownloadSpeed)
        val tvUploadSpeed: TextView = view.findViewById(R.id.tvUploadSpeed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.tvDeviceName.text = device.deviceName
        holder.tvIpMac.text = "IP: \${device.ipAddress} | MAC: \${device.macAddress}"
        holder.tvIpMac.text = "IP: ${device.ipAddress} | MAC: ${device.macAddress}"

        val mbUsed = device.dataUsed / (1024 * 1024)
        holder.tvDataUsed.text = "${mbUsed} MB"

        if (device.isBlocked) {
            holder.tvDeviceName.setTextColor(Color.RED)
            holder.tvDeviceName.text = "${device.deviceName} (BLOCKED)"
            holder.tvDownloadSpeed.text = "↓ 0 KB/s"
            holder.tvUploadSpeed.text = "↑ 0 KB/s"
        } else {
            holder.tvDeviceName.setTextColor(Color.BLACK)
            holder.tvDownloadSpeed.text = "↓ ${formatSpeed(device.downloadSpeed)}"
            holder.tvUploadSpeed.text = "↑ ${formatSpeed(device.uploadSpeed)}"
        }

        // Allow clicking item to trigger Block callback
        holder.itemView.setOnClickListener {
            if (!device.isBlocked) {
                 onBlockClick(device)
            }
        }
    }

    override fun getItemCount() = devices.size

    fun updateData(newDevices: List<Device>) {
        devices = newDevices
        notifyDataSetChanged()
    }
}
