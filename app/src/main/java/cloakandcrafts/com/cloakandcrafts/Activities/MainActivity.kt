package cloakandcrafts.com.cloakandcrafts.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import cloakandcrafts.com.cloakandcrafts.Adapters.SectionPagerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
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
        val extraThread = Thread()

    }

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbReference : CollectionReference = db.collection("locations")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val pagerAdapter = SectionPagerAdapter(supportFragmentManager)
        viewpager.adapter = pagerAdapter

        tabs.setupWithViewPager(viewpager)


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
                    locationsArray = buildArrayList()
                }

        buildCocktailArray(locationsArray!!)
        buildWithFoodArray(locationsArray!!)
        buildSpeakeasyArray(locationsArray!!)
    }//end of on create

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun buildArrayList():ArrayList<BarLocation>{
        var locationsArray : ArrayList<BarLocation> = ArrayList()

        dbReference.get().addOnSuccessListener {
            var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                locationsArray.add(location)
            }
        }
        return locationsArray
    }

    fun buildCocktailArray(locationsArray : ArrayList<BarLocation>){
        for(location in locationsArray){
            if(location.speakeasy==false){
                cocktailArray!!.add(location)
            }
        }
    }

    fun buildWithFoodArray(locationsArray : ArrayList<BarLocation>){
        for(location in locationsArray){
            if(location.food==true){
                withFoodArray!!.add(location)
            }
        }
    }

    fun buildSpeakeasyArray(locationsArray : ArrayList<BarLocation>){
        for(location in locationsArray){
            if(location.speakeasy==true){
                speakeasyArray!!.add(location)
            }
        }
    }

}
