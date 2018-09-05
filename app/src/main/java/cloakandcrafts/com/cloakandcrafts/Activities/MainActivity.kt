package cloakandcrafts.com.cloakandcrafts.Activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity.Companion.REQUEST_PERMISSIONS_REQUEST_CODE
import cloakandcrafts.com.cloakandcrafts.Adapters.SectionPagerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


var locationsArray : ArrayList<BarLocation>? = ArrayList()
var cocktailArray: ArrayList<BarLocation>? = ArrayList()
var speakeasyArray : ArrayList<BarLocation>? = ArrayList()
var withFoodArray : ArrayList<BarLocation>? = ArrayList()

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainActivity"
        val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbReference : CollectionReference = db.collection("locations")
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




        db.collection("locations")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.data)
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                    dbReference = db.collection("locations")
                    buildArrayList()
                    buildWithFoodArray()
                    buildCocktailArray()
                    buildSpeakeasyArray()

                }
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
            else -> super.onOptionsItemSelected(item)
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

    fun buildArrayList(){
        dbReference.get().addOnSuccessListener {
            var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                if(isInRange(location.latitude!!,location.latitude!!)){
                    locationsArray!!.add(location)
                }
            }
        }

    }


    fun buildWithFoodArray(){
        dbReference.get().addOnSuccessListener {
        var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                Log.i(TAG,"Food: " + location.food)
                if (location.food) {
                    if(isInRange(location.latitude!!,location.longitude!!)){
                        withFoodArray!!.add(location)
                        Log.i(TAG, "ADDED TO FOOD ARRAY")
                    }
                }
            }
            Log.i(TAG,"WithFoodArray Built")
        }

    }


    fun buildCocktailArray(){
        dbReference.get().addOnSuccessListener {
            var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                if (!location.speakeasy) {
                    if(isInRange(location.latitude!!,location.longitude!!)){
                        cocktailArray!!.add(location)
                    }
                }
            }
            Log.i(TAG,"Cocktail Array Built")
        }

    }

    fun buildSpeakeasyArray(){
        dbReference.get().addOnSuccessListener {
            var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                if (location.speakeasy) {
                    if(isInRange(location.latitude!!,location.longitude!!)){
                        speakeasyArray!!.add(location)
                    }
                }
            }
            Log.i(TAG,"Speakeasy Array Built")
        }
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
            ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
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
