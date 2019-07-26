package croom.konekom.`in`.a100pitask.activity

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import croom.konekom.`in`.a100pitask.R
import croom.konekom.`in`.a100pitask.adapter.CurrencyItemsAdapter
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.databinding.ActivityMainBinding
import croom.konekom.`in`.a100pitask.model.Currency
import croom.konekom.`in`.a100pitask.model.SuccessResponse
import croom.konekom.`in`.a100pitask.rest.ApiClient
import croom.konekom.`in`.a100pitask.rest.ApiInterface
import croom.konekom.`in`.a100pitask.util.ConnectivityController
import croom.konekom.`in`.a100pitask.viewmodel.HomeViewModel
import croom.konekom.`in`.a100pitask.viewmodel.HomeViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 * Created By Kartikey Kumar Srivastava
 */

class MainActivity : AppCompatActivity() {

    private var apiInterface: ApiInterface? = null
    private val TAG = MainActivity::class.java.simpleName
    private var appDatabase: AppDatabase? = null
    //    private var currencyArrayList: MutableList<Currency>? = null
    private var currencyRecyclerView: RecyclerView? = null
    private var currencyItemsAdapter: CurrencyItemsAdapter? = null
    private var noNetworkView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var toolbar: ActionBar? = null
    var binding: ActivityMainBinding? = null

    //View model variable
    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, HomeViewModelFactory(appDatabase!!, activity.application))
                .get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getInstance(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        addObserver()
        toolbar!!.title = "Currencies"
        setAdapter()

    }

    //Observer for api request and api status
    private fun addObserver() {
        viewModel.currencyArrayList.observe(this, Observer<List<Currency>> { currencies ->
            currencies?.apply {
                if (currencies.size != 0) {
                    currencyItemsAdapter?.currencies = currencies
                    setContentPage()
                    viewModel.updateInProgress()
                }

            }
        })
        viewModel.inProgress.observe(this, Observer {
            if (it == 1) {
                progressBar!!.visibility = View.VISIBLE
                currencyRecyclerView!!.visibility = View.GONE
                noNetworkView!!.visibility = View.GONE
            } else if (it == 3) {
                progressBar!!.visibility = View.GONE
                setNoContentPage()
            } else if(it==2){
                noNetworkView!!.visibility = View.GONE
                progressBar!!.visibility = View.GONE
            }

        })
    }

    //To initialise view and variable
    private fun init() {
        toolbar = supportActionBar

        currencyRecyclerView = binding!!.currenciesList
        noNetworkView = binding!!.networkerror
        progressBar = binding!!.currencyLoader
    }


    //To set network error when their is no network and no data in local database.
    private fun setNoContentPage() {

        currencyRecyclerView!!.visibility = View.GONE
        noNetworkView!!.visibility = View.VISIBLE
    }

    //To set content when value is received
    private fun setContentPage() {
        currencyRecyclerView!!.visibility = View.VISIBLE
        noNetworkView!!.visibility = View.GONE
    }

    //To set adapter for currency list
    private fun setAdapter() {
        currencyItemsAdapter = CurrencyItemsAdapter(this)
        currencyRecyclerView!!.adapter = currencyItemsAdapter
        currencyRecyclerView!!.layoutManager = LinearLayoutManager(this)


        viewModel.currencyArrayList.value?.let {
        currencyItemsAdapter!!.currencies = it
        }

    }


}
