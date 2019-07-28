package croom.konekom.`in`.falabella.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import croom.konekom.`in`.falabella.database.AppDatabase
import croom.konekom.`in`.falabella.model.Beer
import croom.konekom.`in`.falabella.rest.ApiClient
import croom.konekom.`in`.falabella.rest.ApiInterface
import croom.konekom.`in`.falabella.util.ConnectivityController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeerReposiotry(private val appDatabase: AppDatabase, private val app: Context) {

    val beers: LiveData<List<Beer>> = appDatabase.beerDao().allBeer()
    var inProgress = MutableLiveData<Int>()

    suspend fun refreshCurrencies() {

        if (ConnectivityController.isNetworkAvailable(app)) {
            inProgress.value = 1
            withContext(Dispatchers.IO) {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)

                apiInterface.allBeers.enqueue(object : Callback<List<Beer>> {
                    override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {

                        inProgress.value = 2
                        appDatabase.beerDao().nukeTable()
                        appDatabase.beerDao().insertBeer(response.body()!!)
                    }

                    override fun onFailure(call: Call<List<Beer>>, t: Throwable) {

                        checkDatabase()
                    }
                })
            }
        } else {
            checkDatabase()
        }

    }


    fun checkDatabase() {
        if (beers.value != null && beers.value!!.size > 0) {
            inProgress.value = 2
        } else {
            inProgress.value = 3
        }
    }

}