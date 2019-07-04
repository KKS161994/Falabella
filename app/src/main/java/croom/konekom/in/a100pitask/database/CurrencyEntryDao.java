package croom.konekom.in.a100pitask.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import croom.konekom.in.a100pitask.model.Currency;


@Dao
public interface CurrencyEntryDao {
    @Query("SELECT * FROM Currency")
    List<Currency> getAllCurrencies();

    @Insert
    void insertCurrencies(List<Currency> currencyEntries);

    @Query("DELETE FROM Currency")
    public void nukeTable();
}