package cloakandcrafts.com.cloakandcrafts.Activities

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.Adapters.SectionPagerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

var storage = FirebaseStorage.getInstance()
var storageRef = storage.reference
var  imagesRef : StorageReference= storageRef.child("locationImages")

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var milesValue:Int? = null
    var userLocation:Location= Location("userLocation")

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val pagerAdapter = SectionPagerAdapter(supportFragmentManager)
        viewpager.adapter = pagerAdapter

        tabs.setupWithViewPager(viewpager)

        milesValue = getPrefMiles()

    }//end of on create

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                openSettings()
                return true
            }
            R.id.action_contact -> {
                openEmail()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openEmail() {
        val emailAddress= arrayOf("Contact@cloakandcrafts.com")
        val emailSubject = "Cloak & Crafts"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,emailAddress)
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun openSettings(){
        val intent:Intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun getPrefMiles():Int{
        val sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE)
        val prefMiles:Int = sp.getInt("miles", 0)
        return prefMiles
    }

    fun isInRange(latitude:Double,longitude:Double):Boolean{
        var location:Location = Location("point B")
        location.latitude = latitude
        location.longitude = longitude
        Log.i("Distance","User latitude: ${userLocation.latitude.toString()}")
        Log.i("Distance","User Longitude: ${userLocation.longitude.toString()}")

        Log.i("Distance","Location latitude: ${location.latitude.toString()}")
        Log.i("Distance","Location Longitude: ${location.longitude.toString()}")
        var distance:Double = (userLocation!!.distanceTo(location)/1609.34)
        Log.i("Distance","Distance in miles: $distance")
        if(distance<=milesValue!!){
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        userLocation.latitude = task.result.latitude
                        userLocation.longitude = task.result.longitude
                    } else {
                        Toast.makeText(this,"No Location Service Found",Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun checkPermissions() =
            ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
            startLocationPermissionRequest()
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()
            }
        }
    }


}
