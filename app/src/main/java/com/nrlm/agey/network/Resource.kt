package com.nrlm.agey.network

import okhttp3.ResponseBody

sealed class Resource<out T> {

    data class Success<out T>(val value: T): Resource<T>()

    data class Failur(val isNetworkError : Boolean,
                      val errorCode: Int?,
                      val errorbody:ResponseBody?):Resource<Nothing>()
}