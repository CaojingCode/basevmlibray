package com.jijia.kotlinlibrary.entity

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("code")
    var code: Int = 0,
    @SerializedName("msg")
    var msg: String = "",
    @SerializedName("data")
    var data: T? = null,
    var state: AppState = AppState.LOADING,

    var token: String = ""
)


enum class AppState {
    LOADING, SUCCESS, ERROR, EMPTY
}
