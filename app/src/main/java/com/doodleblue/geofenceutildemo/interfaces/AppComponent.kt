package com.doodleblue.geofenceutildemo.interfaces

import android.content.Context
import com.doodleblue.geofenceutildemo.data.repository.GeofenceRepository
import com.doodleblue.geofenceutildemo.view.AddGeofenceFragment
import com.doodleblue.geofenceutildemo.view.GeofenceActivity
import com.doodleblue.geofenceutildemo.view.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun repository(): GeofenceRepository
    fun inject(activity: GeofenceActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(addGeofenceFragment: AddGeofenceFragment)

}