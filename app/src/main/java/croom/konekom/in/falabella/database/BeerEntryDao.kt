package croom.konekom.`in`.falabella.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import croom.konekom.`in`.falabella.model.Beer

@Dao
interface BeerEntryDao {
    @Query("SELECT * FROM Beer")
    fun allBeer(): LiveData<List<Beer>>

    @Insert
    fun insertBeer(currencyEntries: List<Beer>)

    @Query("DELETE FROM Beer")
    fun nukeTable()
}