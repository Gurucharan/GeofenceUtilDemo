package com.doodleblue.geofenceutildemo.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.doodleblue.geofenceutildemo.GeofenceUtilApp
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.util.sendNotification
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsJobIntentService : JobIntentService() {

    companion object {
        private const val TAG = "GeofenceTransitionsJob"

        private const val JOB_ID = 573

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(
                this,
                geofencingEvent.errorCode
            )
            Log.e(TAG, errorMessage)
            return
        }
        handleEvent(geofencingEvent)
    }

    private fun handleEvent(event: GeofencingEvent) {

        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.v(TAG, "Entering Geofence")
        } else {
            Log.v(TAG, "Exiting Geofence")
        }
        val reminder = getFirstReminder(event.triggeringGeofences)
        val message =
            if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) "You are entering in ${reminder?.name}" else "You are exiting from ${reminder?.name}"
        val latLng = reminder?.location
        if (latLng != null) {
            sendNotification(this, message, reminder.id)
        }
    }

    private fun getFirstReminder(triggeringGeofences: List<Geofence>): Reminder? {
        val firstGeofence = triggeringGeofences[0]
        return (application as GeofenceUtilApp).appComponent.repository()
            .getGeofence(firstGeofence.requestId)
    }
}