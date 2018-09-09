package cloakandcrafts.com.cloakandcrafts.Fragments

import android.content.Context.MODE_PRIVATE
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.location_recycler_list.view.*


class CocktailsFragment : Fragment() {

    companion object {
        fun newInstance(): CocktailsFragment {
            return CocktailsFragment()
        }
    }

    var RUNNING:Boolean = false
    var milesValue:Int? = null
    var userLatitude:Double? = null
    var userLongitude:Double? = null

    //Firestore variables
    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbReference : CollectionReference = db.collection("locations")
    var RecyclerAdapter:RecyclerAdapter? = null

    //variables used to pass into setUpRecyclerView()
    lateinit var query : Query
    lateinit var rootView : View

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("MAINACTIVITY","Cocktail Fragment STARTED!!!!!!!!!!!!!!!!!!")
        rootView  =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_cocktails)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_cocktails)
        rootView.sectionTextView.setBackgroundColor(color)

        query = dbReference.whereEqualTo("speakeasy",false)

        setUpRecyclerView(rootView,query)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        RecyclerAdapter!!.startListening()
    }

    override fun onResume() {
        super.onResume()
        if(RUNNING){
            RUNNING=true
            setUpRecyclerView(rootView,query)
        }
    }

    fun setUpRecyclerView(v:View, query:Query){
        //sets up the Recycler View
        milesValue = getPrefMiles()
        userLatitude = getUserLatitude()
        userLongitude = getUserLongitude()

       val options: FirestoreRecyclerOptions<BarLocation> =
                FirestoreRecyclerOptions.Builder<BarLocation>()
                        .setQuery(query,BarLocation::class.java).build()

        RecyclerAdapter = RecyclerAdapter(options)
        val recyclerView:RecyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(v.context)
        recyclerView.adapter = RecyclerAdapter
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
        Log.i("Distance","Location latitude: ${location.latitude.toString()}")
        Log.i("Distance","Location Longitude: ${location.longitude.toString()}")

        val distance:Double = (userLocation.distanceTo(location)/1609.34)
        Log.i("Distance","Distance in miles: $distance")
        if(distance<=getPrefMiles()){
            return true
        }
        return false
    }

    fun getPrefMiles():Int{
        //returns miles set in Settings
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val prefMiles:Int = sharedPref!!.getInt("miles", 99)
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
}


