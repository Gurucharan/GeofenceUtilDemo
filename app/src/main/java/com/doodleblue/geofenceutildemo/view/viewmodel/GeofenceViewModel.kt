package com.doodleblue.geofenceutildemo.view.viewmodel

import androidx.lifecycle.*
import com.doodleblue.geofenceutildemo.data.repository.GeofenceRepository
import com.doodleblue.geofenceutildemo.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GeofenceViewModel @Inject constructor(private val repository: GeofenceRepository) :
    ViewModel() {

    val geofences = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = repository.getAvailableGeofences()
            if (result.isEmpty()) {
                emit(Resource.Error("No data found."))
            } else {
                emit(Resource.Success(result))
            }
        } catch (ioException: Exception) {
            emit(ioException.message?.let { Resource.Error(it) })
        }

    }
}

@Suppress("UNCHECKED_CAST")
class GeofenceViewModelFactory(private val repository: GeofenceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeofenceViewModel::class.java)) {
            return GeofenceViewModel(repository) as T
        }
        throw IllegalArgumentException("No view model class found as ${modelClass.simpleName}.")
    }

}
