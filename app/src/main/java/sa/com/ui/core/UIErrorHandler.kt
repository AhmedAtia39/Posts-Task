package sa.com.ui.core

import sa.com.ui.core.DataState.Error

interface UIErrorHandler {

    fun handleError(error: Error)

    fun showMsgDialog(msg: String)

}