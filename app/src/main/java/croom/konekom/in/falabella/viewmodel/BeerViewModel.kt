package croom.konekom.`in`.falabella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import croom.konekom.`in`.falabella.database.AppDatabase
import croom.konekom.`in`.falabella.model.Beer
import croom.konekom.`in`.falabella.repository.BeerReposiotry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject


class BeerViewModel(appDatabase: AppDatabase, val app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val beerReposiotry = BeerReposiotry(appDatabase, app)
    val beerJson = hashMapOf<Int,Beer>()
    init {
        viewModelScope.launch {

            beerReposiotry.refreshCurrencies()

        }
    }

    var beerArrayList = beerReposiotry.beers
    val inProgress = beerReposiotry.inProgress
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateInProgress() {
        inProgress.value = 2
    }

    fun addBeer(beer: Beer){
        if(beerJson.containsKey(beer.id)){

            beer.count+=1
            beerJson.put(beer.id,beer)
        }
        else{
            beer.count=1
            beerJson.put(beer.id,beer)
        }
    }
    fun removeBeer(beer: Beer){
        if(beerJson.get(beer.id)!!.count==1){

            beerJson.remove(beer.id)
        }
        else{
            val count = beerJson.get(beer.id)
            beer.count-=1
            beerJson.put(beer.id,beer)

        }
    }
}