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
import cloakandcrafts.com.cloakandcrafts.Utilities.ImplicitIntents
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
        val context = rootView.context
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef : StorageReference = storageRef.child("locationImages")

        getIntentData()

        //View Setup
        rootView.detail_locationTitle.setText(mLocation.name)
        rootView.detail_locationTitle.setBackgroundColor(mColor)
        rootView.detail_locationName.setText(mLocation.name)
        rootView.detail_locationName.setTextColor(mColor)
        rootView.detail_address.setText(mLocation.address)
        rootView.detail_phoneNumber.setText(mLocation.phoneNumber)

        val hours="Hours: ${mLocation.hours}\n\n"
        rootView.detail_reviewTextView.setText(hours)

        if(mLocation.review!=null) {
            val stringHolder:String = rootView.detail_reviewTextView.text.toString()
            val reviewText = "$stringHolder ${mLocation.review}"
            rootView.detail_reviewTextView.setText(reviewText)
        }

        if(mLocation.websiteUrl==null){
            rootView.detail_websiteButton.visibility = View.GONE
        }else{
            rootView.detail_websiteButton.setOnClickListener{
                openWebPage(mLocation.websiteUrl.toString())
            }
        }

        if(mLocation.imageId!=null){
            val fileName = "${mLocation.imageName}.jpg"
            val recyclerImage = imagesRef.child(fileName)
            Glide.with(context).load(recyclerImage).into(rootView.detail_locationImage)
        }

        //onClick listeners
        rootView.detail_facebookButton.setOnClickListener{
            openFacebook(context,mLocation.facebookUrl.toString())
        }

        rootView.detail_address.setOnClickListener {
            ImplicitIntents.openMap(context, mLocation)
        }

        rootView.detail_phoneNumber.setOnClickListener {
            ImplicitIntents.openCall(context, mLocation.phoneNumber.toString())
        }

        rootView.fabButton.setOnClickListener { view ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,
                        "$mLocation.name" +
                                System.getProperty("line.separator")+
                                "$mLocation.address" +
                                System.getProperty("line.separator")+
                                "$mLocation.phoneNumber")
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
        val facebookUrl:String
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