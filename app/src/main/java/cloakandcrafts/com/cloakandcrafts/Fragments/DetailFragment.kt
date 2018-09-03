package cloakandcrafts.com.cloakandcrafts.Fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

        val hours:String="Hours: ${mLocation.hours}\n\n"
        rootView.detail_reviewTextView.setText(hours)

        if(mLocation.review!=null) {
            val stringHolder:String = rootView.detail_reviewTextView.text.toString()
            val reviewText:String = "${stringHolder} ${mLocation.review}"
            rootView.detail_reviewTextView.setText(reviewText)
        }

        //attach downloaded image (possible passed through intent)
//        if(mLocation.ImageId==null){
//            rootView.detail_locationImage.visibility = View.GONE
//        }else {
//            rootView.detail_locationImage.setImageResource(mLocation.imageName)
//        }

        if(mLocation.websiteUrl==null){
            rootView.detail_websiteButton.visibility = View.GONE
        }else{
            rootView.detail_websiteButton.setOnClickListener{
                openWebPage(mLocation.websiteUrl.toString())
            }
        }




        rootView.detail_facebookButton.setOnClickListener{
            openFacebook(context!!,mLocation.facebookUrl.toString())
        }

        rootView.fabButton.setOnClickListener { view ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,
                        "${mLocation.name}" +
                                System.getProperty("line.separator")+
                                "${mLocation.address}" +
                                System.getProperty("line.separator")+
                                "${mLocation.phoneNumber}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent,null))
        }

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

    fun openFacebook(context:Context, url:String){
        var facebookUrl:String
        val packageManager:PackageManager = context.packageManager

        try {
            val versionCode:Int=packageManager
                    .getPackageInfo("com.facebook.katana",0).versionCode
            if(versionCode >= 3002850){
                facebookUrl = "fb://facewebmodal/f?href=" + url;
                val facebookIntent = Intent(Intent.ACTION_VIEW)
                facebookIntent.data = Uri.parse(facebookUrl)
                startActivity(facebookIntent)
            }
        }catch (e:PackageManager.NameNotFoundException ){
            openWebPage(url)
        }
    }


}