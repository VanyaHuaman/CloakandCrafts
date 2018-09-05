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
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.location_recycler_list.view.*

class SpeakeasyFragment : Fragment() {
    companion object {
        fun newInstance(): SpeakeasyFragment {
            return SpeakeasyFragment()
        }
    }

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbReference : CollectionReference = db.collection("locations")
    var mRecyclerAdapter: RecyclerAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("MAINACTIVITY","Speakeasy Fragment STARTED!!!!!!!!!!!!!!!!!!")

        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_speakeasy)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_speakeasy)
        rootView.sectionTextView.setBackgroundColor(color)

        var query: Query = dbReference.whereEqualTo("speakeasy",true)

        setUpRecyclerView(rootView,query)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        mRecyclerAdapter!!.startListening()
    }

    fun setUpRecyclerView(v:View,query:Query){
        val options: FirestoreRecyclerOptions<BarLocation> =
                FirestoreRecyclerOptions.Builder<BarLocation>()
                        .setQuery(query, BarLocation::class.java)
                        .build()

        mRecyclerAdapter = RecyclerAdapter(options)
        val recyclerView:RecyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(v.context)
        recyclerView.adapter = mRecyclerAdapter
    }
}