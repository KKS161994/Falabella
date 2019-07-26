package croom.konekom.`in`.a100pitask.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import croom.konekom.`in`.a100pitask.R
import croom.konekom.`in`.a100pitask.model.Currency

/***
 * Created By Kartikey Kumar Srivastava
 */
//Adapter to hold currency data
class CurrencyItemsAdapter(private val context: Context, private val currencies: MutableList<Currency>) : RecyclerView.Adapter<CurrencyItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.currency_item, viewGroup, false)

        // Return a new holder instance
        return ViewHolder(contactView)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val currency = currencies[i]
        //Setting custom object data
        viewHolder.currencyLong.text = currency.currencyLong
        viewHolder.currency.text = currency.currency
        viewHolder.txFee.text = currency.txFee.toString() + ""
    }


    override fun getItemCount(): Int {
        return currencies.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder// We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var currency: TextView
        var currencyLong: TextView
        var txFee: TextView

        init {

            currency = itemView.findViewById(R.id.currency)
            currencyLong = itemView.findViewById(R.id.currencyLong)
            txFee = itemView.findViewById(R.id.txFee)
        }// Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
    }
}