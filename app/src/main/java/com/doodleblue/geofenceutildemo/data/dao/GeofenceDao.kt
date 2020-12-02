package com.doodleblue.geofenceutildemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doodleblue.geofenceutildemo.data.model.Reminder

@Dao
interface GeofenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Query("SELECT * FROM geofence")
    suspend fun getGeofences(): List<Reminder>

    @Query("SELECT * FROM geofence WHERE id = :id")
    fun getGeofence(id: String): Reminder
}