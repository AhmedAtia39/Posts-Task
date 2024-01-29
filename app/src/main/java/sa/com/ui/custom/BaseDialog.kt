package sa.com.ui.custom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class BaseDialog : DialogFragment() {
    private var theDialogView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        theDialogView = onCreateView(LayoutInflater.from(requireContext()), null, savedInstanceState)
        builder.setView(theDialogView)
        return builder.create()
    }

    override fun getView(): View? {
        return theDialogView
    }
}