package croom.konekom.`in`.falabella.viewmodel

import android.app.Application
import androidx.lifecycle.*
import croom.konekom.`in`.falabella.database.AppDatabase

class BeerViewModelFactory(
        private val appDatabase: AppDatabase,
        private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerViewModel::class.java)) {
            return BeerViewModel(appDatabase, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
