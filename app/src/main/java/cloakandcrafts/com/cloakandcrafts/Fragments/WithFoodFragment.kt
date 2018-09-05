package cloakandcrafts.com.cloakandcrafts.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity
import cloakandcrafts.com.cloakandcrafts.Activities.withFoodArray
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.google.firebase.firestore.CollectionReference
import kotlinx.android.synthetic.main.location_recycler_list.view.*

class WithFoodFragment : Fragment() {
    companion object {
        fun newInstance(): WithFoodFragment {

            return WithFoodFragment()
        }
    }



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("MainActivity","With Food Fragment STARTED!!!!!!!!!!!!!!!!!!")
        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_withFood)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_withFood)
        rootView.sectionTextView.setBackgroundColor(color)

        var foodRecyclerView = rootView.recycler_view as RecyclerView
        foodRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        foodRecyclerView!!.adapter = RecyclerAdapter(context!!, withFoodArray!! ,color)


        return rootView
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    fun buildWithFoodArray(dbReference:CollectionReference): ArrayList<BarLocation>{
        dbReference.get().addOnSuccessListener {
            var location : BarLocation
            for (document in it.documents){
                location = document.toObject(BarLocation::class.java)!!
                Log.i(MainActivity.TAG,"Food: " + location.food)
                if (location.food) {
                    withFoodArray!!.add(location)
                    Log.i(MainActivity.TAG, "ADDED TO FOOD ARRAY")
                }
            }

        }
        return withFoodArray!!
    }
}