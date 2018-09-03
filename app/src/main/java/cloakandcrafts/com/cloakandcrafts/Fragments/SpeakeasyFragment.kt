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
import cloakandcrafts.com.cloakandcrafts.Activities.speakeasyArray
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.R
import kotlinx.android.synthetic.main.location_recycler_list.view.*

class SpeakeasyFragment : Fragment() {
    companion object {
        fun newInstance(): SpeakeasyFragment {
            return SpeakeasyFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("MAINACTIVITY","Speakeasy Fragment STARTED!!!!!!!!!!!!!!!!!!")

        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_speakeasy)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_speakeasy)
        rootView.sectionTextView.setBackgroundColor(color)

        var recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(context!!, speakeasyArray!!,color)

        return rootView
    }


}