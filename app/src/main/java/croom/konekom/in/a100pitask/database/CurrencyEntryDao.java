package croom.konekom.in.a100pitask.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import croom.konekom.in.a100pitask.model.Currency;

/***
 * Created By Kartikey Kumar Srivastava
 */

//Dao interface for CRUD operations
@Dao
public interface CurrencyEntryDao {
    @Query("SELECT * FROM Currency")
    List<Currency> getAllCurrencies();

    @Insert
    void insertCurrencies(List<Currency> currencyEntries);

    @Query("DELETE FROM Currency")
    public void nukeTable();
}