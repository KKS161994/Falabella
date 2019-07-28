package croom.konekom.`in`.falabella.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyBoard(context: Context) {
    val manager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = (context as Activity).currentFocus
    if (view != null) {
        manager.hideSoftInputFromWindow(context.currentFocus!!
                .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}