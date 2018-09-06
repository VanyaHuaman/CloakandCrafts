package cloakandcrafts.com.cloakandcrafts.Utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation


class  ImplicitIntents() {

    companion object {
        fun newInstance(): ImplicitIntents {
            return ImplicitIntents()
        }
    }

    fun openEmail(context:Context) {
        val emailAddress= arrayOf("Contact@cloakandcrafts.com")
        val emailSubject = "Cloak & Crafts"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL,emailAddress)
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(context,intent,null)
        }
    }

    fun openMap(context: Context, model:BarLocation){
        val gmmIntentUri = Uri.parse("geo:${model.latitude},${model.longitude}?q=${model.address}+${model.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(context,mapIntent,null)
        }
    }

    fun openCall(context: Context, phoneNumber:String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(context,intent,null)
        }
    }
}