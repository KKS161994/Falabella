package croom.konekom.`in`.falabella.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import croom.konekom.`in`.falabella.adapter.CartItemAdapter
import croom.konekom.`in`.falabella.constant.Constants
import croom.konekom.`in`.falabella.databinding.ActivityCartBinding
import croom.konekom.`in`.falabella.model.Beer
import croom.konekom.`in`.falabella.model.BeerParcel


class CartActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCartBinding
    private var toolbar: ActionBar? = null

    private  var list= ArrayList<BeerParcel>()
    private  var adapterList= listOf<Beer>()

    private var beerRecyclerView: RecyclerView? = null
    private lateinit var cartItemAdapter:CartItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, croom.konekom.`in`.falabella.R.layout.activity_cart)
        toolbar = supportActionBar
        toolbar?.title="Cart"
        toolbar!!.setDisplayHomeAsUpEnabled(true)

        beerRecyclerView = binding!!.beersList

        list = intent.extras.getParcelableArrayList(Constants.HASH_MAP)
        Log.d("Value",list.size.toString())
        setAdapter()
    }

    private fun setAdapter() {
        cartItemAdapter = CartItemAdapter(this)
        beerRecyclerView!!.adapter = cartItemAdapter
        beerRecyclerView!!.layoutManager = LinearLayoutManager(this)
        cartItemAdapter.beers = list

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.getItemId() == android.R.id.home)
        {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}
