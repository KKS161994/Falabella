package croom.konekom.`in`.a100pitask.repository


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.model.Currency
import croom.konekom.`in`.a100pitask.model.SuccessResponse
import croom.konekom.`in`.a100pitask.rest.ApiClient
import croom.konekom.`in`.a100pitask.rest.ApiInterface
import croom.konekom.`in`.a100pitask.util.ConnectivityController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyRepository(private val appDatabase: AppDatabase, private val app: Context) {

    val currencies: LiveData<List<Currency>> = appDatabase.userDao().allCurrencies()
    var inProgress = MutableLiveData<Int>()

    suspend fun refreshCurrencies() {

        if (ConnectivityController.isNetworkAvailable(app)) {
            inProgress.value = 1
            withContext(Dispatchers.IO) {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)

                apiInterface.allCurrencies.enqueue(object : Callback<SuccessResponse> {
                    override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                        inProgress.value = 2
                        appDatabase.userDao().nukeTable()
                        appDatabase.userDao().insertCurrencies(response.body()!!.currencies)
                    }

                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        checkDatabase()
                    }
                })
            }
        } else {
            checkDatabase()
        }

    }


    fun checkDatabase() {

        if (currencies.value != null && currencies.value!!.size > 0) {
            inProgress.value = 2
        } else {
            inProgress.value = 3
        }
    }

}