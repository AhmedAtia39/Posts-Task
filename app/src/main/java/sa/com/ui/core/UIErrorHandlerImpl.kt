package sa.com.ui.core

import androidx.fragment.app.Fragment
import sa.com.R
import sa.com.ui.core.DataState.Error
import sa.com.ui.custom.MessageDialog

class UIErrorHandlerImpl(
    private val fragment: Fragment,
) : UIErrorHandler {

    override fun handleError(error: Error) {
        showMsgDialog(fragment.getString(R.string.error_happened))
    }

    override fun showMsgDialog(msg: String) {
        MessageDialog.newInstance(msg, R.drawable.ic_info_msg)
            .show(fragment.childFragmentManager, MessageDialog.TAG)
    }
}