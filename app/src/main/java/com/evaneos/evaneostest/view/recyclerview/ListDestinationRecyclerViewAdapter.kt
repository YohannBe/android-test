package com.evaneos.evaneostest.view.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.R
import com.evaneos.evaneostest.utils.ID_DESTINATION
import com.evaneos.evaneostest.utils.NAME_DESTINATION
import com.evaneos.evaneostest.view.activities.DetailsActivity

class ListDestinationRecyclerViewAdapter(context: Context) : RecyclerView.Adapter<ListDestinationRecyclerViewAdapter.ViewHolder>() {

    private var data: List<Destination> = ArrayList()
    private val context = context


    fun updateDestinationList(list: List<Destination>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(
                R.layout.destination_element_layout,
                parent,
                false
            )
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(this.context)
            .load(data[position].pictureUrl)
            .into(holder.imageBackground)

        holder.title.text = data[position].name
        holder.ratingBar.rating = data[position].rating.toFloat()
        holder.tag.text = data[position].tag

        holder.layoutContainer.setOnClickListener {
            val toDetailActivity = Intent(context, DetailsActivity::class.java)
            toDetailActivity.putExtra(ID_DESTINATION, data[position].id)
            toDetailActivity.putExtra(NAME_DESTINATION, data[position].name)
            context.startActivity(toDetailActivity)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutContainer: ConstraintLayout = itemView.findViewById(R.id.destination_element_constraint_layout)
        val imageBackground: ImageView = itemView.findViewById(R.id.imageview_background_destination_element)
        val title: TextView = itemView.findViewById(R.id.textview_continent_title_destination_element)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar_indicator_destination_element)
        val tag: TextView = itemView.findViewById(R.id.textview_tag_destination_element)
    }

}