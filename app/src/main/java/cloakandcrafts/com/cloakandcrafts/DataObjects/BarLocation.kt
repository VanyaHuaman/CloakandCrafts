package cloakandcrafts.com.cloakandcrafts.DataObjects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "bar_table")
data class BarLocation(
        @PrimaryKey
        var UQID:Int? = null,
        var speakeasy:Boolean = false,
        var password:Boolean = false,
        var food:Boolean = false,
        var name:String = "",
        var review:String? = null,
        var facebookUrl:String? = null,
        var websiteUrl:String? = null,
        var address:String = "",
        var longitude:Double? = null,
        var latitude:Double? = null,
        var phoneNumber:String? = null,
        var hours:String? = null,
        var imageName:String = "",
        var imageId:Int? = null
): Serializable


