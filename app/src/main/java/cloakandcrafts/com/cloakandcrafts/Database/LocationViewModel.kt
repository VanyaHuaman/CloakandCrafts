package cloakandcrafts.com.cloakandcrafts.Database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LocationRepository = LocationRepository(application)
    val allStores: LiveData<List<BarLocation>>

    init {
        allStores = repository.allLocations
    }

    fun insert(store: BarLocation) {
        repository.insert(store)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

}