package com.jijia.kotlinlibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(layoutId(), null)
        initView(view)
        initData()
        return view
    }



    protected abstract fun initView(view: View)
    protected abstract fun initData()
    protected abstract fun layoutId(): Int
}