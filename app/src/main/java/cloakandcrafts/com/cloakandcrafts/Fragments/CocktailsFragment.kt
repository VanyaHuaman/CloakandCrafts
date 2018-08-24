package cloakandcrafts.com.cloakandcrafts.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.R
import kotlinx.android.synthetic.main.location_recycler_list.view.*


class CocktailsFragment : Fragment() {

    companion object {
        fun newInstance(): CocktailsFragment {
            return CocktailsFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_cocktails)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_cocktails)
        rootView.sectionTextView.setBackgroundColor(color)


        return rootView
    }


}