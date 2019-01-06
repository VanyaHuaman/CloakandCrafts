package cloakandcrafts.com.cloakandcrafts.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.Tag
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity.Companion.RC_SIGN_IN
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity.Companion.authStateListener
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity.Companion.firebaseAuth
import cloakandcrafts.com.cloakandcrafts.Adapters.SectionPagerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.Database.LocationViewModel
import cloakandcrafts.com.cloakandcrafts.R
import cloakandcrafts.com.cloakandcrafts.Utilities.ImplicitIntents
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        var RC_SIGN_IN = 1
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        lateinit var authStateListener: FirebaseAuth.AuthStateListener
        var auth = FirebaseAuth.getInstance()
        lateinit var locationViewModel : LocationViewModel
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        locationViewModel =
                ViewModelProviders.of(this@MainActivity).get(LocationViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        auth = FirebaseAuth.getInstance()
        //checks if user is signed in
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList<AuthUI.IdpConfig>(
                                        AuthUI.IdpConfig.GoogleBuilder().build(),
                                        AuthUI.IdpConfig.EmailBuilder().build()))
                                .build(),
                        RC_SIGN_IN)
            }
        }

        val pagerAdapter = SectionPagerAdapter(supportFragmentManager)
        viewpager.adapter = pagerAdapter

        tabs.setupWithViewPager(viewpager)

    }//end of on create

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            var db : FirebaseFirestore = FirebaseFirestore.getInstance()
            var dbReference = db.collection("locations").get().addOnSuccessListener { documents ->
                var locations = documents.toObjects(BarLocation::class.java)
                locations.forEach {
                    locationViewModel.insert(it)
                }
            }
        }
        getLastLocation()

    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this@MainActivity, "Signed in!", Toast.LENGTH_SHORT).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this@MainActivity, "Sign in Canceled", Toast.LENGTH_SHORT).show()
                finish()
            }
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
                ImplicitIntents.openEmail(this)
                return true
            }
            R.id.action_signout -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun openSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        val sharedPref = getPreferences(Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString("userLatitude", task.result.latitude.toString())
                            commit()
                        }
                        with (sharedPref.edit()) {
                            putString("userLongitude", task.result.longitude.toString())
                            commit()
                        }
                    } else {
                        Toast.makeText(this,"No Location Service Found", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun checkPermissions() =
            ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
            startLocationPermissionRequest()
        } else {
            Log.i(MainActivity.TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        Log.i(MainActivity.TAG, "onRequestPermissionResult")
        if (requestCode == MainActivity.REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(MainActivity.TAG, "User interaction was cancelled.")
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()
            }
        }
    }


}
