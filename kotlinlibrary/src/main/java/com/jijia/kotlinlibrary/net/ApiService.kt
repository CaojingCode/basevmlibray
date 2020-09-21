package com.jijia.kotlinlibrary.net

import com.jijia.kotlinlibrary.entity.ApiResponse
import retrofit2.http.*

 interface  ApiService {

    @GET
    suspend fun <T> httpGet(@Url url: String,@QueryMap map:Map<String,String>): ApiResponse<T>

    @POST
    suspend fun <T> httpPost(@Url url: String,@FieldMap map: Map<String, String>): ApiResponse<T>
}