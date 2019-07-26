package croom.konekom.`in`.a100pitask.viewmodel

import android.app.Application
import androidx.lifecycle.*
import croom.konekom.`in`.a100pitask.database.AppDatabase

class HomeViewModelFactory(
        private val appDatabase: AppDatabase,
        private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appDatabase, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
