package croom.konekom.in.a100pitask.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.util.List;

import croom.konekom.in.a100pitask.R;
import croom.konekom.in.a100pitask.constant.Constants;
import croom.konekom.in.a100pitask.database.AppDatabase;
import croom.konekom.in.a100pitask.model.Currency;

/***
 * Created By Kartikey Kumar Srivastava
 */

//App widget provider that acts as a controller for widget
public class CurrencyWidgetProvider extends AppWidgetProvider {
    int current_pos = 0;                    //To hold position of currency object to be shown currently
    private List<Currency> currencyArrayList;
    private AppDatabase appDatabase;


    private static final String NEXT_CURRENCY = "nextcurrency";
    private static final String PREVIOUS_CURRENCY = "previouscurrency";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

//Called to update the widget
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        appDatabase = AppDatabase.getInstance(context);
        sharedpreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        if (!sharedpreferences.contains(Constants.CURRENT_POS)) {
            sharedpreferences.edit().putInt(Constants.CURRENT_POS, 0).apply();
        }
        current_pos = sharedpreferences.getInt(Constants.CURRENT_POS, 0);


        for (int appwidgetId : appWidgetIds) {

            currencyArrayList = appDatabase.userDao().getAllCurrencies();
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

            if (currencyArrayList.size() > 0) {
                views.setTextViewText(R.id.currency, currencyArrayList.get(current_pos).getCurrency());
                views.setTextViewText(R.id.currencyLong, currencyArrayList.get(current_pos).getCurrencyLong());
                views.setTextViewText(R.id.txFee, currencyArrayList.get(current_pos).getTxFee() + "");
                views.setOnClickPendingIntent(R.id.previousCurrency,
                        getPendingSelfIntent(context, PREVIOUS_CURRENCY));
                views.setOnClickPendingIntent(R.id.nextCurrency,
                        getPendingSelfIntent(context, NEXT_CURRENCY));
            }
            appWidgetManager.updateAppWidget(appwidgetId, views);
        }
    }

    //Preparing intent for onClickAction
    private PendingIntent getPendingSelfIntent(Context context, String action) {
        // An explicit intent directed at the current class (the "self").
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    //Calling update to update widget with new currency either previous or next
    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance
                (context);
        ComponentName thisAppWidgetComponentName =
                new ComponentName(context.getPackageName(), getClass().getName()
                );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                thisAppWidgetComponentName);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    //Function that gets called when on click event happens
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        sharedpreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        appDatabase = AppDatabase.getInstance(context);

        currencyArrayList = appDatabase.userDao().getAllCurrencies();

        if (PREVIOUS_CURRENCY.equals(intent.getAction())) {
            current_pos = sharedpreferences.getInt(Constants.CURRENT_POS, 0);
            if (current_pos != 0) {


                current_pos = current_pos - 1;
                editor = sharedpreferences.edit();
                editor.putInt(Constants.CURRENT_POS, current_pos);
                editor.apply();

            }


            onUpdate(context);
        } else if (NEXT_CURRENCY.equals(intent.getAction())) {
            current_pos = sharedpreferences.getInt(Constants.CURRENT_POS, 0);

            if (current_pos != currencyArrayList.size() - 1) {

                current_pos = current_pos + 1;
                editor = sharedpreferences.edit();
                editor.putInt(Constants.CURRENT_POS, current_pos);
                editor.apply();

            }

            onUpdate(context);
        }
    }


}
