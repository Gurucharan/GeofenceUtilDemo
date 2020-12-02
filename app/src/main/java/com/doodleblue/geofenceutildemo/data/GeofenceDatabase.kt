package com.doodleblue.geofenceutildemo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doodleblue.geofenceutildemo.data.dao.GeofenceDao
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.util.DATABASE_VERSION

@Database(entities = [Reminder::class], version = DATABASE_VERSION, exportSchema = false)
abstract class GeofenceDatabase : RoomDatabase() {

    abstract fun geofenceDao(): GeofenceDao

}