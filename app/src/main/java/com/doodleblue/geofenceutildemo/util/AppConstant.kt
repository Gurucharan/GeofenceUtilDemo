package com.doodleblue.geofenceutildemo.util

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "geofence.db"
const val GEOFENCE_RADIUS_IN_METERS = 500f

enum class Type{
    NAME,
    LATITUDE,
    LONGITUDE
}