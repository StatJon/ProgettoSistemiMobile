package com.unibo.mobile.data.remote.api

import okio.IOException
import retrofit2.HttpException

/**
 * Custom Class to handle API calls and errors (instead of wrapper for every single call).
 * To be used by Repository classes for every API call as a wrapper.
 * Example:
 * class RepositoryExampleImpl (safeApiCaller: SafeApiCaller){
 *  val example = safeApiCaller.invoke { dndApi.apiCall() }
 * }
 */
class SafeApiCaller {
    suspend fun <T> invoke(apiCall: suspend () -> T): T? {
        return try {
            val result = apiCall()
            println("DEBUG: API SUCCESS: $result")
            result
        } catch (e: HttpException) {
            print(e)
            null
        } catch (e: IOException) {
            print("DEBUG: API ERROR: ${e.message}")
            null
        }
    }
}