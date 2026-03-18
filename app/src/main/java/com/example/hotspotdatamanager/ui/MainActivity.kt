package com.example.hotspotdatamanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hotspotdatamanager.R
import com.example.hotspotdatamanager.service.HotspotManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private lateinit var hotspotManager: HotspotManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hotspotManager = HotspotManager(this)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val switchHotspot = findViewById<SwitchCompat>(R.id.switchHotspot)

        switchHotspot.setOnCheckedChangeListener { _, isChecked ->
            hotspotManager.toggleHotspot(isChecked)
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_overview -> {
                    loadFragment(OverviewFragment())
                    true
                }
                R.id.nav_devices -> {
                    loadFragment(DevicesFragment())
                    true
                }
                else -> false
            }
        }

        // Load default fragment
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_overview
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
