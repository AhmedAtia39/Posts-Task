package sa.com.ui.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize
import sa.com.databinding.DialogLoadingBinding

class LoadingDialog : BaseDialog() {

    companion object {
        const val TAG = "loading_dialog"
        fun newInstance(
            requestKey: String = "${TAG}_default_key",
            cancelable: Boolean = false
        ): LoadingDialog {
            return LoadingDialog().apply {
                arguments = bundleOf("ARGS" to Args(requestKey, cancelable))
            }
        }
    }

    private lateinit var args: Args

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        // Set transparent background to get round corners
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val binding = DialogLoadingBinding.inflate(layoutInflater, container, false)
        args = requireArguments().getParcelable("ARGS") ?: throw IllegalArgumentException(
            "require argument ARGS"
        )
        isCancelable = args.cancelable

        return binding.root
    }

    @Parcelize
    class Args(
        val requestKey: String,
        val cancelable: Boolean
    ) : Parcelable

}