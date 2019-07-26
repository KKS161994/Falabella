package croom.konekom.`in`.a100pitask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.model.Currency
import croom.konekom.`in`.a100pitask.repository.CurrencyRepository
import croom.konekom.`in`.a100pitask.util.ConnectivityController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel(appDatabase: AppDatabase, val app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val currencyRepository = CurrencyRepository(appDatabase, app)

    init {
        viewModelScope.launch {

            currencyRepository.refreshCurrencies()

        }
    }

    var currencyArrayList = currencyRepository.currencies
    val inProgress = currencyRepository.inProgress
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateInProgress() {
        inProgress.value = 2
    }
}