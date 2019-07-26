package croom.konekom.`in`.a100pitask.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.RemoteViews

import croom.konekom.`in`.a100pitask.R
import croom.konekom.`in`.a100pitask.constant.Constants
import croom.konekom.`in`.a100pitask.database.AppDatabase
import croom.konekom.`in`.a100pitask.model.Currency

/***
 * Created By Kartikey Kumar Srivastava
 */

//App widget provider that acts as a controller for widget
class CurrencyWidgetProvider : AppWidgetProvider() {
    internal var current_pos = 0                    //To hold position of currency object to be shown currently
    private var currencyArrayList: List<Currency>? = null
    private var appDatabase: AppDatabase? = null
    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null


    //Called to update the widget
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appDatabase = AppDatabase.getInstance(context)
        sharedpreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE)
        if (!sharedpreferences!!.contains(Constants.CURRENT_POS)) {
            sharedpreferences!!.edit().putInt(Constants.CURRENT_POS, 0).apply()
        }
        current_pos = sharedpreferences!!.getInt(Constants.CURRENT_POS, 0)


        for (appwidgetId in appWidgetIds) {

            currencyArrayList = appDatabase!!.userDao().allCurrencies
            val views = RemoteViews(context.packageName, R.layout.app_widget)

            if (currencyArrayList!!.size > 0) {
                views.setTextViewText(R.id.currency, currencyArrayList!![current_pos].currency)
                views.setTextViewText(R.id.currencyLong, currencyArrayList!![current_pos].currencyLong)
                views.setTextViewText(R.id.txFee, currencyArrayList!![current_pos].txFee.toString() + "")
                views.setOnClickPendingIntent(R.id.previousCurrency,
                        getPendingSelfIntent(context, PREVIOUS_CURRENCY))
                views.setOnClickPendingIntent(R.id.nextCurrency,
                        getPendingSelfIntent(context, NEXT_CURRENCY))
            }
            appWidgetManager.updateAppWidget(appwidgetId, views)
        }
    }

    //Preparing intent for onClickAction
    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        // An explicit intent directed at the current class (the "self").
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    //Calling update to update widget with new currency either previous or next
    private fun onUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
                thisAppWidgetComponentName)
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    //Function that gets called when on click event happens
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        sharedpreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE)
        appDatabase = AppDatabase.getInstance(context)

        currencyArrayList = appDatabase!!.userDao().allCurrencies

        if (PREVIOUS_CURRENCY == intent.action) {
            current_pos = sharedpreferences!!.getInt(Constants.CURRENT_POS, 0)
            if (current_pos != 0) {


                current_pos = current_pos - 1
                editor = sharedpreferences!!.edit()
                editor!!.putInt(Constants.CURRENT_POS, current_pos)
                editor!!.apply()

            }


            onUpdate(context)
        } else if (NEXT_CURRENCY == intent.action) {
            current_pos = sharedpreferences!!.getInt(Constants.CURRENT_POS, 0)

            if (current_pos != currencyArrayList!!.size - 1) {

                current_pos = current_pos + 1
                editor = sharedpreferences!!.edit()
                editor!!.putInt(Constants.CURRENT_POS, current_pos)
                editor!!.apply()

            }

            onUpdate(context)
        }
    }

    companion object {


        private val NEXT_CURRENCY = "nextcurrency"
        private val PREVIOUS_CURRENCY = "previouscurrency"
    }


}
