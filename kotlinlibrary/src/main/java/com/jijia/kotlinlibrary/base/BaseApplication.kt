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
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.ScreenUtils

abstract class BaseApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        configWebViewCacheDirWithAndroidP()
        RetrofitManage.initRetrofit(getBaseUrl())
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG).setConsoleSwitch(BuildConfig.DEBUG).globalTag = "BaseAndroid"
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        //在 Demo 中跳转的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance().onAdaptListener = object :onAdaptListener{
            override fun onAdaptBefore(target: Any?, activity: Activity?) {
                AutoSizeConfig.getInstance().screenWidth = ScreenUtils.getScreenSize(activity)[0];
                AutoSizeConfig.getInstance().screenHeight = ScreenUtils.getScreenSize(activity)[1];
            }

            override fun onAdaptAfter(target: Any?, activity: Activity?) {
            }

        }
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