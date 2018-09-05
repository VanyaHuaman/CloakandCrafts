package cloakandcrafts.com.cloakandcrafts.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.DetailActivity
import cloakandcrafts.com.cloakandcrafts.Activities.imagesRef
import cloakandcrafts.com.cloakandcrafts.DataObjects.BarLocation
import cloakandcrafts.com.cloakandcrafts.R.layout.list_item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerAdapter(
        val context:Context,
        val locations: ArrayList<BarLocation>,
        val colorResourceID:Int)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View =
                LayoutInflater.from(parent.context).inflate(list_item, parent, false)
        val holder = ViewHolder(view)

        holder.parentLayout.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("location", locations[holder.adapterPosition])
            intent.putExtra("color", colorResourceID)
            context.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentLocation:BarLocation = locations.get(position)

        holder.locationName.setText(currentLocation.name)
        holder.locationAddress.setText(currentLocation.address)
        if(currentLocation.ImageId!=null){
            val fileName:String = "${currentLocation.imageName.toString()}.jpg"
            val recyclerImage = imagesRef.child(fileName)
            Glide.with(context).load(recyclerImage).into(holder.locationImage)
        }else{
            holder.locationImage.visibility = View.GONE
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

            val parentLayout = itemView.rootLayout
            val locationName = itemView.locationName_textView
            val locationAddress = itemView.locationAddress_textView
            val locationImage = itemView.locationImage
    }
}