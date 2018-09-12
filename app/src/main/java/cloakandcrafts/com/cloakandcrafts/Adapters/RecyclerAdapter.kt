package cloakandcrafts.com.cloakandcrafts.Adapters

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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(options: FirestoreRecyclerOptions<BarLocation>) : FirestoreRecyclerAdapter<BarLocation, RecyclerAdapter.customHolder>(options) {

    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        context = parent.context
        return customHolder(v)
    }

    override fun onBindViewHolder(holder: customHolder, position: Int, model: BarLocation) {
        holder.locationName.text = model.name
        holder.locationAddress.text = model.address
        val colorResourceID = getColor(model)

        //Firestore variables
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef : StorageReference = storageRef.child("locationImages")

        //checks and sets image
        if(model.imageId!=null){
            val fileName = "${model.imageName}.jpg"
            val recyclerImage = imagesRef.child(fileName)
            Glide.with(context).load(recyclerImage).into(holder.locationImage)
        }else{
            holder.locationImage.visibility = View.GONE
        }

        //onClick listener
        holder.parentLayout.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("location", model)
            intent.putExtra("color", colorResourceID)
            context.startActivity(intent)
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