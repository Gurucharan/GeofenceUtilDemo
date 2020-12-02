package com.doodleblue.geofenceutildemo.view.viewmodel

import androidx.lifecycle.*
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.data.repository.GeofenceRepository
import com.doodleblue.geofenceutildemo.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddGeofenceViewModel @Inject constructor(private val repository: GeofenceRepository) :
    ViewModel() {

    private var _submitStatus = MutableLiveData<Resource<Boolean>>()
    val submitStatus: LiveData<Resource<Boolean>>
        get() = _submitStatus

    fun addGeofence(reminder: Reminder) {
        _submitStatus.value = Resource.Loading()
        repository.addGeofence(reminder, success = {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.addReminder(reminder)
                    _submitStatus.postValue(Resource.Success(true))
                } catch (ioException: Exception) {
                    _submitStatus.postValue(Resource.Error(ioException.message.toString()))
                }
            }
        }, failure = {
            _submitStatus.value = Resource.Error(it)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class AddGeofenceViewModelFactory(private val repository: GeofenceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGeofenceViewModel::class.java)) {
            return AddGeofenceViewModel(repository) as T
        }
        throw IllegalArgumentException("No view model class found as ${modelClass.simpleName}.")
    }

}