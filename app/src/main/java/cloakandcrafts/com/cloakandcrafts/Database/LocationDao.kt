package cloakandcrafts.com.cloakandcrafts.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

@Dao
interface LocationDao {

    @Query("SELECT * from bar_table ")
    fun allLocations(): LiveData<List<BarLocation>>

    @Query("SELECT * from bar_table where speakeasy = 1")
    fun allSpeakeasys(): LiveData<List<BarLocation>>

    @Query("SELECT * from bar_table where speakeasy = 0")
    fun allCocktailBars(): LiveData<List<BarLocation>>

    @Query("SELECT * from bar_table where food = 1")
    fun allBarsWithFood(): LiveData<List<BarLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bar: BarLocation)

    @Query("DELETE FROM bar_table")
    fun deleteAll()

}
