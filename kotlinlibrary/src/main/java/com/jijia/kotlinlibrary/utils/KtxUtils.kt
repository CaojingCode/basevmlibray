package com.jijia.kotlinlibrary.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.entity.ApiResponse
import com.jijia.kotlinlibrary.entity.AppState
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog


fun Activity.fullScreen() {
    val window: Window = this.window
    window.clearFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
    )
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
    }
}



/**
 * CustomTabEntity 集合数据
 * 主要用来给CommonTabLayout 填充数据，首页底部导航栏
 */
fun MutableList<CustomTabEntity>.addData(
    tittles: ArrayList<String>,
    unSelectedIds: ArrayList<Int>,
    selectedIds: ArrayList<Int>
): ArrayList<CustomTabEntity> {
    for (i in tittles.indices) {
        add(object : CustomTabEntity {
            override fun getTabUnselectedIcon(): Int {
                return unSelectedIds[i]
            }

            override fun getTabSelectedIcon(): Int {
                return selectedIds[i]
            }

            override fun getTabTitle(): String {
                return tittles[i]
            }

        })
    }
    return this as ArrayList<CustomTabEntity>
}


@SuppressLint("WrongConstant")
fun Context.permissionRequest(permission: String, block: () -> Unit) {
    var permissions = PermissionConstantsKt.getPermissions(permission)
    var isGranted = PermissionUtils.isGranted(*permissions)
    if (!isGranted) {
        PermissionUtils.permission(*permissions)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    block()
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    ToastUtils.showLong(deniedForever.toString() + "\n" + denied.toString())
                    this@permissionRequest.commonDialog(
                        "需要使用到存储，相机，电话等相关权限，"
                                + "\n请在设置-应用-${AppUtils.getAppName()}-权限中开启相关权限，" +
                                "以正常使用${AppUtils.getAppName()}功能"
                    ) {
                        //跳转到权限设置页面
                        PermissionUtils.launchAppDetailsSettings()
                    }.show()
                }


            })
            .request()
    } else {
        LogUtils.d("同意的权限")
        block()
    }
}

/**
 * 通用对话框
 */
fun Context.commonDialog(content: String, block: () -> Unit): QMUIDialog {
    var dialog = QMUIDialog.MessageDialogBuilder(this)
    dialog.setMessage(content)
    dialog.addAction(QMUIDialogAction("确定", QMUIDialogAction.ActionListener { dialog, index ->
        block()
    }))
    dialog.addAction(QMUIDialogAction("取消", QMUIDialogAction.ActionListener { dialog, index ->
        dialog.dismiss()
    }))
    return dialog.create()
}

fun Context.loadingDialog(): QMUITipDialog {
    return QMUITipDialog.Builder(this)
        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
        .create(false)
}

fun <T> AppLiveData<ApiResponse<T>>.appObserve(
    activity: AppCompatActivity,
    onSuccess: (T) -> Unit,//接口返回成功
    onError: () -> Unit = {},//接口返回失败，如果需要处理失败逻辑时，可以传人错误方法的逻辑
    onEmpty: () -> Unit = {} //接口返回空数据 ，如果需要护理空数据逻辑，可以传人空数据方法的处理逻辑
) {
    this.observe(activity, Observer {
        when (it.state) {
            AppState.ERROR -> {
                onError()
            }
            AppState.EMPTY -> {
                onEmpty()
            }
            AppState.SUCCESS -> {
                var data = it.data
                if (data is List<*>) {
                    if (data.size > 0) {
                        onSuccess(data)
//                        ToastUtils.showShort("返回不为空=集合")
                    } else {
                        onEmpty()
//                        ToastUtils.showShort("返回数据为空=集合")
                    }
                } else {
                    if (data != null) {
                        onSuccess(data)
//                        ToastUtils.showShort("返回不为空")
                    }else{
                        onEmpty()
//                        ToastUtils.showShort("返回数据为空")
                    }
                }

            }
        }
    })
}



