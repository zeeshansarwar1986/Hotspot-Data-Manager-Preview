package com.example.hotspotdatamanager.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotspotdatamanager.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class OverviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineChart = view.findViewById<LineChart>(R.id.lineChart)
        val tvTotalData = view.findViewById<TextView>(R.id.tvTotalData)
        val tvActiveDevices = view.findViewById<TextView>(R.id.tvActiveDevices)

        // Mock Data setup for Demo
        tvTotalData.text = "450 MB"
        tvActiveDevices.text = "2"

        setupChart(lineChart)
    }

    private fun setupChart(chart: LineChart) {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 25f))
        entries.add(Entry(3f, 15f))
        entries.add(Entry(4f, 40f))
        entries.add(Entry(5f, 35f))

        val dataSet = LineDataSet(entries, "Data Usage (MB)")
        dataSet.color = Color.parseColor("#FF6200EE")
        dataSet.valueTextColor = Color.BLACK
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(true)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.parseColor("#FFBB86FC")

        val lineData = LineData(dataSet)
        chart.data = lineData

        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.animateX(1000)
        chart.invalidate()
    }
}
