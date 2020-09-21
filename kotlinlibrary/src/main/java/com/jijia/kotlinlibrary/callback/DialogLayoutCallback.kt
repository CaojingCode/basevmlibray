package com.jijia.kotlinlibrary.callback

import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment

interface DialogLayoutCallback {

    fun bindTheme(): Int

    fun bindLayout(): Int

    fun initView(dialog: DialogFragment, contentView: View)

    fun setWindowStyle(window: Window)

    fun onCancel(dialog: DialogFragment)

    fun onDismiss(dialog: DialogFragment)

}