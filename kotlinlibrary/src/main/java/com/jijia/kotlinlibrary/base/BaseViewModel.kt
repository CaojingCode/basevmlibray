package com.jijia.kotlinlibrary.base

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.reflect.TypeToken
import com.jijia.kotlinlibrary.entity.ApiResponse
import com.jijia.kotlinlibrary.entity.AppState
import com.jijia.kotlinlibrary.net.ApiService
import com.jijia.kotlinlibrary.net.RetrofitManage
import java.lang.reflect.Type

open class BaseViewModel : ViewModel() {

    private var baseUrl: String = "https://www.wanandroid.com/"


    private val apiService: ApiService by lazy {
        RetrofitManage.retrofit.create(ApiService::class.java)
    }

    /**
     * get请求公共方法
     */
    suspend fun <T> getData(
        apiName: String,
        type: Type,
        host: String = baseUrl,
        map: Map<String, String> = HashMap()
    ): ApiResponse<T> {
        var apiResponse = ApiResponse<T>(data = null)
        var isSuccess = CatchException.catch {
            apiResponse = apiService.httpGet(host + apiName, map)
        }
        setState(isSuccess, apiResponse)
        return GsonUtils.fromJson(
            GsonUtils.toJson(apiResponse), type
        )
    }

    /**
     * post请求公共方法
     */
    suspend fun <T> postData(
        apiName: String,
        type: Type,
        host: String = baseUrl,
        map: Map<String, String> = HashMap()
    ): ApiResponse<T> {
        var apiResponse = ApiResponse<T>()
        var isSuccess = CatchException.catch {
            apiResponse = apiService.httpPost(host + apiName, map)
        }
        setState(isSuccess, apiResponse)
        return GsonUtils.fromJson(
            GsonUtils.toJson(apiResponse), type
        )
    }

    /**
     * 设置接口请求状态枚举值
     */
    private fun <T> setState(isSuccess: Boolean, apiResponse: ApiResponse<T>) {
        if (!isSuccess) {
            apiResponse.state = AppState.ERROR
        } else {
            if (apiResponse.code == 0) {
                if (apiResponse.data == null) {
                    apiResponse.state = AppState.EMPTY
                } else {
                    apiResponse.state = AppState.SUCCESS
                }
            } else {
                apiResponse.state = AppState.ERROR
            }
        }
    }
}

