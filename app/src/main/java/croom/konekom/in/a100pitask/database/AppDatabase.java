package croom.konekom.in.a100pitask.database;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import croom.konekom.in.a100pitask.model.Currency;
/***
 * Created By Kartikey Kumar Srivastava
 */

//Database to store values locally
@Database(entities = {Currency.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract CurrencyEntryDao userDao();

    private static final String DB_NAME = "TestTask";

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}