package com.example.countries.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

/*This function is used to closed the keyboard when search query is submitted*/
fun hideSoftKeyboard(activity: Activity){
    val manager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if(manager.isAcceptingText){
        manager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,
            0
        )
    }
}