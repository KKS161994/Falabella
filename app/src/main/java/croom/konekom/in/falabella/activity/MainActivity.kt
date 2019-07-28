package croom.konekom.`in`.falabella.activity


import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import croom.konekom.`in`.falabella.adapter.BeerItemAdapter
import croom.konekom.`in`.falabella.database.AppDatabase
import croom.konekom.`in`.falabella.databinding.ActivityMainBinding
import croom.konekom.`in`.falabella.viewmodel.BeerViewModel
import croom.konekom.`in`.falabella.viewmodel.BeerViewModelFactory
import android.os.StrictMode.VmPolicy
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import croom.konekom.`in`.falabella.R
import croom.konekom.`in`.falabella.adapter.BeerItemClickListener
import croom.konekom.`in`.falabella.constant.Constants
import croom.konekom.`in`.falabella.model.Beer
import croom.konekom.`in`.falabella.model.BeerParcel
import croom.konekom.`in`.falabella.model.convertTo
import croom.konekom.`in`.falabella.util.hideKeyBoard


/***
 * Created By Kartikey Kumar Srivastava
 */

class MainActivity : AppCompatActivity() {


    private var appDatabase: AppDatabase? = null
    private var beerRecyclerView: RecyclerView? = null
    private var beerItemAdapter: BeerItemAdapter? = null
    private var noNetworkView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var toolbar: ActionBar? = null
    var binding: ActivityMainBinding? = null
    private lateinit var filterText: TextView
    private lateinit var cancelFilter: ImageView

    //View model variable
    private val viewModel: BeerViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, BeerViewModelFactory(appDatabase!!, activity.application))
                .get(BeerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStrictMode()
        appDatabase = AppDatabase.getInstance(this)
        binding = DataBindingUtil.setContentView(this, croom.konekom.`in`.falabella.R.layout.activity_main)
        init()
        addObserver()
        toolbar!!.title = "Falabella"
        setAdapter()

    }


    //Observer for api request and api status
    private fun addObserver() {
        viewModel.beerArrayList.observe(this, Observer<List<Beer>> { beers ->
            beers.apply {
                if (beers.size != 0) {
                    beerItemAdapter?.beers = beers
                    setContentPage()
                    viewModel.updateInProgress()
                }

            }
        })
        viewModel.inProgress.observe(this, Observer {
            if (it == 1) {
                progressBar!!.visibility = View.VISIBLE
                beerRecyclerView!!.visibility = View.GONE
                noNetworkView!!.visibility = View.GONE
            } else if (it == 3) {
                progressBar!!.visibility = View.GONE
                setNoContentPage()
            } else if (it == 2) {
                noNetworkView!!.visibility = View.GONE
                progressBar!!.visibility = View.GONE
            }

        })
    }

    //To initialise view and variable
    private fun init() {
        toolbar = supportActionBar
        filterText = binding!!.bearFilterText
        beerRecyclerView = binding!!.beersList
        noNetworkView = binding!!.networkerror
        progressBar = binding!!.currencyLoader
        cancelFilter = binding!!.crossBtn
        setTextChangedListener()
        setcancelFilter()
    }

    private fun setcancelFilter() {
        cancelFilter.setOnClickListener(View.OnClickListener {
            filterText.text = ""
            beerItemAdapter!!.beers = viewModel.beerArrayList.value!!

        })
    }

    //To set text change Listener for filter
    private fun setTextChangedListener() {
        filterText.setOnClickListener(View.OnClickListener {
            filterText.setCursorVisible(true)
            filterText.setFocusable(true)
        })

        filterText.setOnEditorActionListener(TextView.OnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                filterText.setCursorVisible(false)
                hideKeyBoard(this)
                return@OnEditorActionListener true
            }
            false
        })
        filterText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.length > 0) {
                        cancelFilter.visibility = View.VISIBLE
                        val newList = mutableListOf<Beer>()
                        viewModel.beerArrayList.value!!.forEach {
                            if (it.name.startsWith(s.toString(), true)) {
                                newList.add(it)
                            }
                        }
                        beerItemAdapter!!.beers = newList
                    } else {
                        cancelFilter.visibility = View.GONE
                    }

                }
            }

        })

    }


    //To set network error when their is no network and no data in local database.
    private fun setNoContentPage() {

        beerRecyclerView!!.visibility = View.GONE
        noNetworkView!!.visibility = View.VISIBLE
    }

    //To set content when value is received
    private fun setContentPage() {
        beerRecyclerView!!.visibility = View.VISIBLE
        noNetworkView!!.visibility = View.GONE
    }

    //To set adapter for beer list
    private fun setAdapter() {
        beerItemAdapter = BeerItemAdapter(this, BeerItemClickListener {
            item,type->
            if(type==Constants.ADD)
                viewModel.addBeer(item)
            else
                viewModel.removeBeer(item)

        })
        beerRecyclerView!!.adapter = beerItemAdapter
        beerRecyclerView!!.layoutManager = LinearLayoutManager(this)


        viewModel.beerArrayList.value?.let {
            beerItemAdapter!!.beers = it
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.cartIcon-> {
                val intent = Intent(this,CartActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelableArrayList(Constants.HASH_MAP,getList(viewModel.beerJson))
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun getList(beerJsons: HashMap<Int, Beer>) :ArrayList<BeerParcel>{
        var cartItems = arrayListOf<BeerParcel>()
       for (item in beerJsons.entries) {
   cartItems.add(item.value.convertTo())
}
        return cartItems
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus,menu)
        return true
    }

}
