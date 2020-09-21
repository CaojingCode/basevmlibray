package com.jijia.kotlinlibrary.entity

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("errorCode")
    var code: Int = 0,
    @SerializedName("errorMsg")
    var msg: String = "",
    @SerializedName("data")
    var data: T? =null,
    var state: AppState=AppState.LOADING
)


enum class AppState {
    LOADING, SUCCESS, ERROR, EMPTY
}
