package com.jijia.kotlinlibrary.base

import androidx.lifecycle.MutableLiveData

class AppLiveData<T> :MutableLiveData<T>(){

    override fun onActive() {
        super.onActive()
    }
    override fun onInactive() {
        super.onInactive()
    }


}