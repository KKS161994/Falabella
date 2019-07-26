package croom.konekom.`in`.a100pitask.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import croom.konekom.`in`.a100pitask.model.Currency

/***
 * Created By Kartikey Kumar Srivastava
 */

//Dao interface for CRUD operations
@Dao
interface CurrencyEntryDao {
    @get:Query("SELECT * FROM Currency")
    val allCurrencies: List<Currency>

    @Insert
    fun insertCurrencies(currencyEntries: List<Currency>)

    @Query("DELETE FROM Currency")
    fun nukeTable()
}