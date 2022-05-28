package com.ahmedwagdy.bostatask.network.api

import android.util.Log
import retrofit2.Response

//import okhttp3.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("Error ->> ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error("Exception ->> ${e.message ?: e.toString()}")
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}