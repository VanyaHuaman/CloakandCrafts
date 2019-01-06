package cloakandcrafts.com.cloakandcrafts.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation

@Database(entities = [BarLocation::class], version = 1)
abstract class LocationRoomDatabase : RoomDatabase(){

    abstract fun locationDao() : LocationDao

    companion object {

        var INSTANCE: LocationRoomDatabase? = null

        fun getDatabase(context: Context): LocationRoomDatabase {
            if (INSTANCE == null) {
                synchronized(LocationRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                LocationRoomDatabase::class.java, "locationdatabase")
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}