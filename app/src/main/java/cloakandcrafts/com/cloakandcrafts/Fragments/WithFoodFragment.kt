package cloakandcrafts.com.cloakandcrafts.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.cocktailArray
import cloakandcrafts.com.cloakandcrafts.Activities.withFoodArray
import cloakandcrafts.com.cloakandcrafts.Adapters.RecyclerAdapter
import cloakandcrafts.com.cloakandcrafts.R
import kotlinx.android.synthetic.main.location_recycler_list.view.*

class WithFoodFragment : Fragment() {
    companion object {
        fun newInstance(): WithFoodFragment {
            return WithFoodFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_withFood)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_withFood)
        rootView.sectionTextView.setBackgroundColor(color)

        var recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(context!!, withFoodArray!!,color)

        return rootView
    }

}