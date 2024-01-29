package sa.com.ui.core

import androidx.fragment.app.Fragment
import sa.com.ui.custom.LoadingDialog

class UILoadingHandlerImpl(
    private val fragment: Fragment,
) : UILoadingHandler {

    override fun setLoading(show: Boolean) {
        if (show) {
            LoadingDialog.newInstance().show(fragment.childFragmentManager, LoadingDialog.TAG)
        } else {
            (fragment.childFragmentManager.findFragmentByTag(LoadingDialog.TAG) as LoadingDialog?)?.dismiss()
        }
    }

}