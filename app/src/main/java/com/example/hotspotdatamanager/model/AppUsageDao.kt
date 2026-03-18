package com.example.hotspotdatamanager.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppUsageDao {
    @Query("SELECT * FROM app_usage WHERE macAddress = :mac")
    fun getUsageForDevice(mac: String): LiveData<List<AppUsage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usage: AppUsage)

    @Update
    suspend fun update(usage: AppUsage)

    @Query("DELETE FROM app_usage WHERE macAddress = :mac")
    suspend fun deleteForDevice(mac: String)
}
