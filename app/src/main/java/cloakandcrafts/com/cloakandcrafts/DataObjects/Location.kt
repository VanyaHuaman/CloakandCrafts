package cloakandcrafts.com.cloakandcrafts.DataObjects

import java.io.Serializable

data class Location(
        val speakEasy:Boolean,
        val password:Boolean,
        val food:Boolean,
        val name:String,
        var instructions:String? = null,
        var facebookUrl:String? = null,
        var websiteUrl:String? = null,
        var address:String,
        var longitude:String? = null,
        var latitude:String? = null,
        var phoneNumber:String?,
        var hoursArray:Array<String?> = arrayOfNulls<String?>(7),
        var ImageId:Int? = null
) : Serializable{
}