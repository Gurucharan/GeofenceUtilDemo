package com.doodleblue.geofenceutildemo.data.repository

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.doodleblue.geofenceutildemo.data.dao.GeofenceDao
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.service.GeofenceBroadcastReceiver
import com.doodleblue.geofenceutildemo.service.GeofenceErrorMessages
import com.doodleblue.geofenceutildemo.util.GEOFENCE_RADIUS_IN_METERS
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class GeofenceRepository @Inject constructor(
    private val context: Context,
    private val geofenceDao: GeofenceDao
) {

    private val geofencingClient = LocationServices.getGeofencingClient(context)

    private var mGeofencePendingIntent: PendingIntent? = null
    private val geofencePendingIntent: PendingIntent?
        private get() {
            // Reuse the PendingIntent if we already have it.
            if (mGeofencePendingIntent != null) {
                return mGeofencePendingIntent
            }
            val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
            // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
            // addGeofences() and removeGeofences().
            mGeofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return mGeofencePendingIntent
        }

    fun addGeofence(
        reminder: Reminder,
        success: () -> Unit,
        failure: (error: String) -> Unit
    ) {
        val geofence = buildGeofence(reminder)
        if (geofence != null
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient
                .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                    success()
                }
                .addOnFailureListener {
                    failure(GeofenceErrorMessages.getErrorString(context, it))
                }
        }
    }

    private fun buildGeofence(reminder: Reminder): Geofence? {
        val latitude = reminder.location.latitude
        val longitude = reminder.location.longitude
        val radius = GEOFENCE_RADIUS_IN_METERS
        return Geofence.Builder()
            .setRequestId(reminder.id)
            .setCircularRegion(
                latitude,
                longitude,
                radius
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(listOf(geofence))
            .build()
    }

    suspend fun addReminder(reminder: Reminder) {
        geofenceDao.insert(reminder)
    }

    suspend fun getAvailableGeofences() = geofenceDao.getGeofences()

    fun getGeofence(id: String) = geofenceDao.getGeofence(id)

}