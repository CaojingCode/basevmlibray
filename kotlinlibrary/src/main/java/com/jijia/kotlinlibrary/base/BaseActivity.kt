package com.jijia.kotlinlibrary.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jijia.kotlinlibrary.BuildConfig
import com.jijia.kotlinlibrary.R
import com.jijia.kotlinlibrary.R.id.viewLine
import com.jijia.kotlinlibrary.utils.fullScreen
import kotlinx.android.synthetic.main.activity_base.*
import okhttp3.*
import java.io.IOException

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        fullScreen()
        setContentLayout(layoutResID())
        initView()
        initData()
    }

    abstract fun layoutResID(): Int


    protected abstract fun initView()
    protected abstract fun initData()


    private fun setContentLayout(layoutId: Int = -1) {
        if (layoutId == -1) {
            return
        }
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(layoutId, null)
        val params = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0
        )
        params.topToBottom = R.id.viewLine
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        baseView.addView(contentView, params)
        ivBack.setOnClickListener {
            backAction()
        }
        ivRight.setOnClickListener {
            rightAction()
        }
    }

    /**
     * 返回键监听
     */
    fun backAction() {
        finish()
    }

    /**
     * 隐藏标题栏，默认显示
     */
    fun titleBarHide() {
        groupBar.visibility = View.GONE
    }

    /**
     * 隐藏返回键，默认显示
     */
    fun backBtnHide() {
        groupBack.visibility = View.GONE
    }

    fun setGoneRight(isGone: Boolean) = when {
        isGone -> {
            ivRight.visibility = View.GONE
        }
        else -> {
            ivRight.visibility = View.VISIBLE
        }
    }


   open fun rightAction() {

    }

    fun setTittle(text: String) {
        tvTittle.text = text
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}