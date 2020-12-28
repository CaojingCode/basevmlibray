package com.jijia.kotlinlibrary.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity4MainProcess
import com.jijia.kotlinlibrary.BuildConfig
import com.jijia.kotlinlibrary.net.RetrofitManage

abstract class BaseApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        configWebViewCacheDirWithAndroidP()
        RetrofitManage.initRetrofit(getBaseUrl())
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG).setConsoleSwitch(BuildConfig.DEBUG).globalTag = "BaseAndroid"
    }

    private fun configWebViewCacheDirWithAndroidP(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            var processName = getProcessName();
            if (AppUtils.getAppPackageName() != processName) {//判断不等于默认进程名称
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    abstract fun getBaseUrl():String
}