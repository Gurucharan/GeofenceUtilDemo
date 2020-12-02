package com.doodleblue.geofenceutildemo.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "geofence")
data class Reminder(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String?,
    @Embedded
    val location: Location
)

data class Location(val latitude: Double, val longitude: Double)