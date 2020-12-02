package com.doodleblue.geofenceutildemo

import android.app.Application
import com.doodleblue.geofenceutildemo.interfaces.AppComponent
import com.doodleblue.geofenceutildemo.interfaces.DaggerAppComponent

class GeofenceUtilApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}