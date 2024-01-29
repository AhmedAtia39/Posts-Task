package sa.com.ui.core

import sa.com.ui.core.DataState.Error

interface VMErrorHandler {

    fun handleError(error: Error)

}