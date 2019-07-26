package croom.konekom.`in`.a100pitask.activity

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import croom.konekom.`in`.a100pitask.R
import croom.konekom.`in`.a100pitask.adapter.CurrencyItemsAdapter
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.model.Currency
import croom.konekom.`in`.a100pitask.model.SuccessResponse
import croom.konekom.`in`.a100pitask.rest.ApiClient
import croom.konekom.`in`.a100pitask.rest.ApiInterface
import croom.konekom.`in`.a100pitask.util.ConnectivityController
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
    private var currencyArrayList: MutableList<Currency>? = null
    private var currencyRecyclerView: RecyclerView? = null
    private var currencyItemsAdapter: CurrencyItemsAdapter? = null
    private var noNetworkView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var toolbar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        toolbar!!.title = "Currencies"
        currencyArrayList = ArrayList()
        if (ConnectivityController.isNetworkAvailable(this)) {
            loadDataFromInternet()
        } else {
            loadDataFromDatabase()
        }

    }

    //To initialise view and variable
    private fun init() {
        toolbar = supportActionBar
        appDatabase = AppDatabase.getInstance(this)
        currencyRecyclerView = findViewById(R.id.currenciesList)
        noNetworkView = findViewById(R.id.networkerror)
        progressBar = findViewById(R.id.currency_loader)
    }

    //To load data from local database when network is not present or API call fails
    private fun loadDataFromDatabase() {
        currencyArrayList!!.addAll(appDatabase!!.userDao().allCurrencies)
        if (currencyArrayList!!.size > 0) {
            setAdapter()
        } else {
            setNoContentPage()
        }
    }

    //To set network error when their is no network and no data in local database.
    private fun setNoContentPage() {
        currencyRecyclerView!!.visibility = View.GONE
        noNetworkView!!.visibility = View.VISIBLE
    }

    //To set adapter for currency list
    private fun setAdapter() {
        currencyItemsAdapter = CurrencyItemsAdapter(this, currencyArrayList!!)
        currencyRecyclerView!!.adapter = currencyItemsAdapter
        currencyRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    //To load data from API
    private fun loadDataFromInternet() {
        apiInterface = ApiClient.client.create(ApiInterface::class.java)
        progressBar!!.visibility = View.VISIBLE
        val successResponseCall = apiInterface!!.allCurrencies

        successResponseCall.enqueue(object : Callback<SuccessResponse> {
            override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                //Toast.makeText(MainActivity.this, response.code() + " " + response.body().getCurrencies().size(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Api call success" + response.body()!!.currencies.size)
                progressBar!!.visibility = View.GONE
                saveCurrencyToDatabase(response.body()!!.currencies)
            }

            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Log.d(TAG, "Api call fail$t")
                progressBar!!.visibility = View.GONE
                loadDataFromDatabase()
            }
        })
    }

    //Saving the list to database
    private fun saveCurrencyToDatabase(currencies: List<Currency>) {
        appDatabase!!.userDao().nukeTable()
        appDatabase!!.userDao().insertCurrencies(currencies)
        currencyArrayList!!.addAll(appDatabase!!.userDao().allCurrencies)
        setAdapter()

    }

}
