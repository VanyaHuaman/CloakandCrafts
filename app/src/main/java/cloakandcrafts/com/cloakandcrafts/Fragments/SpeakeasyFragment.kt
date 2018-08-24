package cloakandcrafts.com.cloakandcrafts.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val rootView : View =
                inflater.inflate(R.layout.location_recycler_list, container, false)

        rootView.sectionTextView.setText(R.string.section_speakeasy)
        val color : Int = ContextCompat.getColor(rootView.context,R.color.section_speakeasy)
        rootView.sectionTextView.setBackgroundColor(color)


        return rootView
    }

}