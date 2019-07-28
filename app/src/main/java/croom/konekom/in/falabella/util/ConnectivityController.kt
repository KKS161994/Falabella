package croom.konekom.`in`.falabella.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

/***
 * Created By Kartikey Kumar Srivastava
 */

//Class to check whether internet is available or not
object ConnectivityController {

    val TAG = ConnectivityController::class.java.name

    /**
     * Checking is connected to Internet
     */

    fun isNetworkAvailable(context: Context): Boolean {

        try {
            val cm = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {

                return true
            }

        } catch (e: Exception) {
            Log.e(TAG, "Unable to check is internet Avaliable", e)
        }

        return false
    }

}
