package sa.com.ui.core

import sa.com.data.MainRepo
import sa.com.ui.core.DataState.Error
import sa.com.ui.core.DataState.Error.AuthenticationError

class VMErrorHandlerImpl(
    private val mainRepo: MainRepo,
) : VMErrorHandler {

    override fun handleError(error: Error) {
        when (error) {
            is AuthenticationError -> {}
            else -> { /* do nothing */ }
        }
    }

}