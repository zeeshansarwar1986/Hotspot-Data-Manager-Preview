package com.example.hotspotdatamanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotspotdatamanager.R
import com.example.hotspotdatamanager.viewmodel.MainViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class DeviceDetailsFragment : Fragment() {

    private lateinit var macAddress: String
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        macAddress = arguments?.getString("mac_address") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_device_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val circularTotal = view.findViewById<CircularProgressIndicator>(R.id.circularTotal)
        val tvTotalCenter = view.findViewById<TextView>(R.id.tvTotalCenter)
        val tvIncoming = view.findViewById<TextView>(R.id.tvIncoming)
        val tvOutgoing = view.findViewById<TextView>(R.id.tvOutgoing)
        val rvAppUsage = view.findViewById<RecyclerView>(R.id.rvAppUsage)

        rvAppUsage.layoutManager = LinearLayoutManager(requireContext())

        // Observer for device data
        viewModel.devices.observe(viewLifecycleOwner) { devices ->
            val device = devices.find { it.macAddress == macAddress }
            device?.let {
                tvTotalCenter.text = "${it.dataUsed / (1024 * 1024)} MB"
                tvIncoming.text = "${(it.dataUsed * 0.9).toLong() / (1024 * 1024)} MB"
                tvOutgoing.text = "${(it.dataUsed * 0.1).toLong() / (1024 * 1024)} MB"
                circularTotal.progress = 75 // Mock ratio
            }
        }

        // Observer for app usage (simulated)
        // In a real app, you would have a separate LiveData for app usage
    }

    companion object {
        fun newInstance(mac: String) = DeviceDetailsFragment().apply {
            arguments = Bundle().apply { putString("mac_address", mac) }
        }
    }
}
