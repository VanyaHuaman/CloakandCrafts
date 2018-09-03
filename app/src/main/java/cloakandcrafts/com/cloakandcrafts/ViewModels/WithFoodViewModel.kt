package cloakandcrafts.com.cloakandcrafts.ViewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cloakandcrafts.com.cloakandcrafts.Activities.locationsArray
import cloakandcrafts.com.cloakandcrafts.Activities.withFoodArray
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

class DataViewModel : ViewModel(){
    var withFood : MutableLiveData<ArrayList<BarLocation>> = MutableLiveData()

    fun getWithFoodArray(): LiveData<ArrayList<BarLocation>> {
        if(withFood==null){
            withFood = MutableLiveData<ArrayList<BarLocation>>()
            updateWithFoodArray()
        }
        return withFood
    }

    private fun updateWithFoodArray() {
        for(location in locationsArray!!){
            if(location.food){
                withFood.value!!.add(location)
            }
        }
    }

}








