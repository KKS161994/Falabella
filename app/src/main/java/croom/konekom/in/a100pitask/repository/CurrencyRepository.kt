package croom.konekom.`in`.a100pitask.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.model.Currency
import croom.konekom.`in`.a100pitask.model.SuccessResponse
import croom.konekom.`in`.a100pitask.rest.ApiClient
import croom.konekom.`in`.a100pitask.rest.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyRepository(private val appDatabase: AppDatabase){

val currencies:LiveData<List<Currency>> = appDatabase.userDao().allCurrencies
    suspend fun refreshCurrencies(){
        withContext(Dispatchers.IO){
            val apiInterface = ApiClient.client.create(ApiInterface::class.java)
            apiInterface.allCurrencies.enqueue(object : Callback<SuccessResponse> {
                override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                    //Toast.makeText(MainActivity.this, response.code() + " " + response.body().getCurrencies().size(), Toast.LENGTH_SHORT).show();

                    appDatabase.userDao().nukeTable()
                    appDatabase.userDao().insertCurrencies(response.body()!!.currencies!!)
                }

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {

                }
            })



        }
    }

}