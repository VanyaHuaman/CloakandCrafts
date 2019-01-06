package cloakandcrafts.com.cloakandcrafts.Database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

class LocationRepository internal constructor(application: Application){

    private val locationDao : LocationDao
    val allLocations : LiveData<List<BarLocation>>
    val allSpeakeasys : LiveData<List<BarLocation>>
    val allCocktailBars : LiveData<List<BarLocation>>
    val allBarsWithFood : LiveData<List<BarLocation>>

    init {
        val db = LocationRoomDatabase.getDatabase(application)
        locationDao = db.locationDao()
        allLocations = locationDao.allLocations()
        allSpeakeasys = locationDao.allSpeakeasys()
        allCocktailBars = locationDao.allCocktailBars()
        allBarsWithFood = locationDao.allBarsWithFood()
    }

    fun deleteAll() {
        locationDao.deleteAll()
    }

    fun insert(store: BarLocation) {
        insertAsyncTask(locationDao).execute(store)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: LocationDao) : AsyncTask<BarLocation, Void, Void>() {
        override fun doInBackground(vararg params: BarLocation): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}