package cloakandcrafts.com.cloakandcrafts.Adapters

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.DataObjects.Location
import cloakandcrafts.com.cloakandcrafts.R.layout.list_item
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(
        val context:Context,
        val locations: ArrayList<Location>,
        val colorResourceID:Int)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View =
                LayoutInflater.from(parent.context).inflate(list_item, parent, false)
        val holder:ViewHolder = ViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentLocation:Location = locations.get(position)

        holder.locationName.setText(currentLocation.name)
        holder.locationAddress.setText(currentLocation.address)
        if(currentLocation.ImageId!=null){
            holder.locationImage.setImageResource(currentLocation.ImageId!!)
        }else{
            holder.locationImage.visibility = View.GONE
        }
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

            val parentLayout = itemView.rootLayout
            val locationName = itemView.locationName_textView
            val locationAddress = itemView.locationAddress_textView
            val locationImage = itemView.locationImage
            val position : Int? = null
    }
}