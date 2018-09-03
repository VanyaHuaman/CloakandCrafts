package cloakandcrafts.com.cloakandcrafts.DataObjects

import java.io.Serializable

data class BarLocation(
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
        var ImageId:Int? = null
): Serializable


