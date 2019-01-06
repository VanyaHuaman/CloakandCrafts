package cloakandcrafts.com.cloakandcrafts.Database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LocationRepository = LocationRepository(application)
    val allLocations: LiveData<List<BarLocation>>
    val allSpeakeasys : LiveData<List<BarLocation>>
    val allCocktailBars : LiveData<List<BarLocation>>
    val allBarsWithFood : LiveData<List<BarLocation>>

    init {
        allLocations = repository.allLocations
        allSpeakeasys = repository.allSpeakeasys
        allCocktailBars = repository.allCocktailBars
        allBarsWithFood = repository.allBarsWithFood
    }

    fun insert(store: BarLocation) {
        repository.insert(store)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

}