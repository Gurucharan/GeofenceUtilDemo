package com.doodleblue.geofenceutildemo.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

private const val TAG = "GeofenceBroadcast"

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
    }

}


