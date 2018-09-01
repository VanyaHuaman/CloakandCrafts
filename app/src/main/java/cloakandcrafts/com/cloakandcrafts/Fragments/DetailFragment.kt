package cloakandcrafts.com.cloakandcrafts.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import kotlinx.android.synthetic.main.activity_details.view.*

class DetailFragment : Fragment(){

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    private var mColor: Int = 0
    private var mLocation: BarLocation = BarLocation()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView : View =
                inflater.inflate(R.layout.activity_details, container, false)

        getIntentData()

        rootView.detail_locationTitle.setText(mLocation.name)
        rootView.detail_locationTitle.setBackgroundColor(mColor)
        rootView.detail_locationName.setText(mLocation.name)
        rootView.detail_locationName.setTextColor(mColor)
        rootView.detail_address.setText(mLocation.address)
        rootView.detail_phoneNumber.setText(mLocation.phoneNumber)
        rootView.detail_reviewTextView.setText(mLocation.review)

//        if(mLocation.ImageId==null){
//            rootView.detail_locationImage.visibility = View.GONE
//        }else {
//            rootView.detail_locationImage.setImageResource(mLocation.ImageId.toString())
//        }

        if(mLocation.websiteUrl==null){
            rootView.detail_websiteButton.visibility = View.GONE
        }else{
            rootView.detail_websiteButton.setOnClickListener{
                openWebPage(mLocation.websiteUrl.toString())
            }
        }

        rootView.detail_facebookButton.setOnClickListener{
            openWebPage(mLocation.facebookUrl.toString())
        }

//        fab.setOnClickListener { view ->
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT,
//                        "${mLocation.name}/n${mLocation.address}/n${mLocation.phoneNumber}")
//                type = "text/plain"
//            }
//            startActivity(Intent.createChooser(sendIntent,null))
//        }

        return rootView
    }

    fun getIntentData(){
        mColor = activity!!.intent.getIntExtra("color",0)
        mLocation = activity!!.intent.getSerializableExtra("location") as BarLocation
    }

    fun openWebPage(url:String){
        val uris = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }


}