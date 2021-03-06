package cloakandcrafts.com.cloakandcrafts.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.DetailActivity
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(locationArray: MutableList<BarLocation>, activity:Activity) : RecyclerView.Adapter<RecyclerAdapter.customHolder>() {

    lateinit var context:Context
    var passedActivity = activity
    var locations = locationArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        context = parent.context
        return customHolder(v)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: customHolder, position: Int) {
        holder.locationName.text = locations[position].name
        holder.locationAddress.text = locations[position].address
        val colorResourceID = getColor(locations[position])

        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef : StorageReference = storageRef.child("locationImages")

        //checks and sets image
        if(locations[position].imageId!=null){
            val fileName = "${locations[position].imageName}.jpg"
            val recyclerImage = imagesRef.child(fileName)
            Glide.with(context).load(recyclerImage).into(holder.locationImage)
        }else{

            Glide.with(context).load(R.drawable.default_loc_image).into(holder.locationImage)
        }

        //onClick listener
        holder.parentLayout.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                    passedActivity, holder.locationImage,"locationImage"
            )
            intent.putExtra("location", locations[position])
            intent.putExtra("color", colorResourceID)
            context.startActivity(intent,options.toBundle())
        }
    }




    private fun getColor(model:BarLocation): Int {
        //returns the color based on the type of category
        if(model.food){
            return ContextCompat.getColor(context,R.color.section_withFood)
        }else if(model.speakeasy){
            return ContextCompat.getColor(context,R.color.section_speakeasy)
        }else{
            return ContextCompat.getColor(context,R.color.section_cocktails)
        }
    }

    //Custom view Holder Class
    class customHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val parentLayout = itemView.rootLayout
        val locationName = itemView.locationName_textView
        val locationAddress = itemView.locationAddress_textView
        val locationImage = itemView.locationImage
    }


}