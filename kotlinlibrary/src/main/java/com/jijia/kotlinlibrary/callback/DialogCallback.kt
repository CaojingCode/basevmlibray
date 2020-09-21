package com.jijia.kotlinlibrary.callback

import android.app.Activity
import android.app.Dialog
import android.view.Window


interface  DialogCallback {

    fun bindDialog(activity: Activity): Dialog

    fun setWindowStyle(window: Window)

}