package croom.konekom.`in`.falabella.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import croom.konekom.`in`.falabella.R
import croom.konekom.`in`.falabella.model.Beer
import croom.konekom.`in`.falabella.model.BeerParcel

class CartItemAdapter(private val context: Context) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    var beers=ArrayList<BeerParcel>()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartItemAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.cart_beer_item, viewGroup, false)

        // Return a new holder instance
        return ViewHolder(contactView)

    }

    override fun onBindViewHolder(viewHolder:CartItemAdapter.ViewHolder, position: Int) {
        val beer = beers[position]
        //Setting custom object data
        viewHolder.beer_name.text = beer.name
        viewHolder.beer_style.text = beer.style
        viewHolder.alcoholContent.text = beer.abv
        viewHolder.beerCount.text = beer.count.toString()
    }



    private fun getCount(viewHolder: ViewHolder):Int{
        return viewHolder.beerCount.text.toString().toInt()
    }


    override fun getItemCount(): Int {
        return beers.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder// We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var beer_name: TextView
        var beer_style: TextView
        var alcoholContent: TextView

        var beerCount: TextView
        init {

            beer_name = itemView.findViewById(R.id.beer_name)
            beer_style = itemView.findViewById(R.id.beer_style)
            alcoholContent = itemView.findViewById(R.id.alcoholContent)

            beerCount = itemView.findViewById(R.id.beerCount)
        }// Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
    }
}