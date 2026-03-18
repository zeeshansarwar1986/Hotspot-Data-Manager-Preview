package com.example.hotspotdatamanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspotdatamanager.R
import com.example.hotspotdatamanager.viewmodel.MainViewModel
import com.example.hotspotdatamanager.service.HotspotManager
import com.example.hotspotdatamanager.service.ControlUtil

class DevicesFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: DeviceAdapter
    private lateinit var hotspotManager: HotspotManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_devices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        hotspotManager = HotspotManager(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDevices)

        val onBlockClick: (com.example.hotspotdatamanager.model.Device) -> Unit = { device ->
            ControlUtil.blockDevice(requireContext(), device.macAddress)
            viewModel.blockDevice(device)
        }

        adapter = DeviceAdapter(emptyList(), onBlockClick)
        recyclerView.adapter = adapter

        val btnRestartHotspot = view.findViewById<Button>(R.id.btnRestartHotspot)
        btnRestartHotspot.setOnClickListener {
            btnRestartHotspot.isEnabled = false
            btnRestartHotspot.text = "Restarting..."
            Toast.makeText(requireContext(), "Restarting hotspot...", Toast.LENGTH_SHORT).show()
            hotspotManager.restartHotspot {
                btnRestartHotspot.isEnabled = true
                btnRestartHotspot.text = "Restart Hotspot"
                Toast.makeText(requireContext(), "Hotspot Restarted Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe LiveData from ViewModel
        viewModel.devices.observe(viewLifecycleOwner) { devices ->
            adapter.updateData(devices)
        }
    }
}
