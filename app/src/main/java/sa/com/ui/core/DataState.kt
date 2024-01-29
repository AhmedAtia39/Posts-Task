package sa.com.ui.core

import sa.com.data.remote.model.ApiError
import java.io.IOException


sealed class DataState<out T : Any>(var consumed: Boolean = false) {

    /**
     * General
     */
    class Idle : DataState<Nothing>()
    class Loading : DataState<Nothing>()
    class Empty : DataState<Nothing>()
    data class Success<out R : Any>(val data: R) : DataState<R>()

    /**
     * Errors
     */
    sealed class Error : DataState<Nothing>() {
        class AuthenticationError : Error()
        data class ClientError(val error: ApiError?) : Error()
        class NetworkError(val error: IOException) : Error()
        class UnknownError(val error: Throwable) : Error()
    }

    fun successOrNull(): Success<T>? {
        return if (this is Success)
            return this
        else
            null
    }
}

