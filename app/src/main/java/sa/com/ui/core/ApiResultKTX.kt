package sa.com.ui.core

import com.slack.eithernet.ApiResult
import sa.com.data.remote.model.ApiError

fun <E : ApiError> ApiResult.Failure<E>.toErrorState(): DataState<Nothing> {
    return when (this) {
        is ApiResult.Failure.NetworkFailure -> DataState.Error.NetworkError(error)
        is ApiResult.Failure.UnknownFailure -> DataState.Error.UnknownError(error)
        is ApiResult.Failure.HttpFailure -> error?.toState() ?: DataState.Error.ClientError(null)
        is ApiResult.Failure.ApiFailure -> error?.toState() ?: DataState.Error.ClientError(null)
    }

}

fun ApiError.toState(): DataState<Nothing> {
    return when (errorCode) {
        "20" -> DataState.Empty()
        "57" -> DataState.Error.AuthenticationError()
        else -> DataState.Error.ClientError(this)
    }
}