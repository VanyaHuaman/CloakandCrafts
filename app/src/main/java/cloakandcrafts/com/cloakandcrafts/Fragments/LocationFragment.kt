package cloakandcrafts.com.cloakandcrafts.Fragments

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.location.Location
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.location_recycler_list.view.*

abstract class LocationFragment : Fragment() {

    var milesValue:Int? = null
    var userLatitude:Double? = null
    var userLongitude:Double? = null
    lateinit var currentGeoPoint: GeoPoint
    abstract var fragmentName:String

    //Firestore variables
    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbReference : CollectionReference = db.collection("locations")
    lateinit var options: FirestoreRecyclerOptions<BarLocation>
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var recyclerView: RecyclerView

    //variables used to pass into setUpRecyclerView()
    lateinit var query : Query
    lateinit var rootView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPrefMiles()
        currentGeoPoint = getUserGeoPoint()

        db = FirebaseFirestore.getInstance()
        dbReference = db.collection("locations")

        setFragmentQuery(fragmentName)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView  =
                inflater.inflate(R.layout.location_recycler_list, container, false)
        setUpRecyclerView(rootView,query)
        setFragmentTitle(fragmentName)
        setFragmentColor(fragmentName)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        recyclerAdapter.startListening()
    }


    fun setUpRecyclerView(v:View, query:Query){
        //sets up the Recycler View
        milesValue = getPrefMiles()
        userLatitude = getUserLatitude()
        userLongitude = getUserLongitude()
        val options: FirestoreRecyclerOptions<BarLocation> =
                FirestoreRecyclerOptions.Builder<BarLocation>()
                        .setQuery(query, BarLocation::class.java).build()

        val activity : Activity = activity as MainActivity
        recyclerAdapter = RecyclerAdapter(options,activity)
        val recyclerView: RecyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(v.context)
        recyclerView.adapter = recyclerAdapter
    }

    fun isInRange(userLatitude:Double,userLongitude:Double,latitude:Double,longitude:Double):Boolean{
        //checks if Location('point B') is within radius of user location('point A')

        val userLocation = Location("point A")
        userLocation.latitude = userLatitude
        userLocation.longitude = userLongitude

        val location = Location("point B")
        location.latitude = latitude
        location.longitude = longitude

        Log.i("Distance","User latitude: ${userLocation.latitude}")
        Log.i("Distance","User Longitude: ${userLocation.longitude}")
        Log.i("Distance","Location latitude: ${location.latitude}")
        Log.i("Distance","Location Longitude: ${location.longitude}")

        val distance:Double = (userLocation.distanceTo(location)/1609.34)
        Log.i("Distance","Distance in miles: $distance")
        if(distance<=getPrefMiles()){
            return true
        }
        return false
    }

    fun getPrefMiles():Int{
        //returns miles set in Settings
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val prefMiles:Int = sharedPref.getInt("miles", 99)
        Log.i("distance","Retrieved Miles: $prefMiles")
        return prefMiles
    }

    fun getUserLatitude():Double{
        //returns user location latitude
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val latitude:Double = sharedPref!!.getString("userLatitude", "0").toDouble()
        Log.i("distance","Retrieved latitude: $latitude")
        return latitude
    }

    fun getUserLongitude():Double{
        //returns user location longitude
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val longitude:Double = sharedPref!!.getString("userLongitude", "0").toDouble()
        Log.i("distance","Retrieved longitude: $longitude")
        return longitude
    }

    fun getUserGeoPoint():GeoPoint{
        return GeoPoint(getUserLatitude(),getUserLongitude())
    }

    fun setFragmentColor(fragmentName:String){
        val color:Int
        when (fragmentName) {
            "cocktails" -> color = ContextCompat.getColor(rootView.context, R.color.section_cocktails)
            "speakeasy" -> color = ContextCompat.getColor(rootView.context, R.color.section_speakeasy)
            else -> color = ContextCompat.getColor(rootView.context, R.color.section_withFood)
        }
        rootView.sectionTextView.setBackgroundColor(color)
    }

    fun setFragmentTitle(fragmentName: String){
        val name:String
        when (fragmentName) {
            "cocktails" -> name = getString(R.string.section_cocktails)
            "speakeasy" -> name = getString(R.string.section_speakeasy)
            else -> name = getString(R.string.section_withFood)
        }
        rootView.sectionTextView.text = name
    }

    fun setFragmentQuery(fragmentName: String){
        val name:String
        val bool:Boolean
        when (fragmentName) {
            "withFood" -> name = "food"
            else -> name = "speakeasy"
        }

        when (fragmentName) {
            "cocktails" -> bool = false
            else -> bool = true
        }

        Log.i("Fragment","Passed Name: $name")
        Log.i("Fragment","Passed Bool: $bool")

        query = dbReference.whereEqualTo(name,bool)

    }

}