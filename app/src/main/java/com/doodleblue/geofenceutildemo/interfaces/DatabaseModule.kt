package com.doodleblue.geofenceutildemo.interfaces

import android.content.Context
import androidx.room.Room
import com.doodleblue.geofenceutildemo.data.GeofenceDatabase
import com.doodleblue.geofenceutildemo.data.dao.GeofenceDao
import com.doodleblue.geofenceutildemo.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideDatabase(context: Context): GeofenceDatabase {
        return Room.databaseBuilder(context, GeofenceDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideGeofenceDao(database: GeofenceDatabase): GeofenceDao {
        return database.geofenceDao()
    }
}