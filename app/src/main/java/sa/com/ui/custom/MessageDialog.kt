package sa.com.ui.custom

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import kotlinx.parcelize.Parcelize
import sa.com.R
import sa.com.databinding.DialogMessageBinding


class MessageDialog : BaseDialog() {

    companion object {
        const val TAG = "message_dialog"
        fun newInstance(
            message: CharSequence,
            @DrawableRes icon: Int = R.drawable.ic_info_msg,
            cancelable:Boolean = true,
            requestKey: String = TAG
        ): MessageDialog {
            val dialog = MessageDialog()
            dialog.arguments = bundleOf("args" to Args(message, icon, cancelable, requestKey))
            return dialog
        }
    }

    private lateinit var mBinding: DialogMessageBinding
    private lateinit var args: Args

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = arguments?.getParcelable("args")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = DialogMessageBinding.inflate(layoutInflater, container, false)
        isCancelable = args.cancelable

        mBinding.tvMessage.text = args.message
        mBinding.tvMessage.movementMethod = ScrollingMovementMethod()
        mBinding.ivIcon.setImageResource(args.icon)
        mBinding.btnOk.setOnClickListener {
            setFragmentResult(args.requestKey, bundleOf("resultCode" to Activity.RESULT_OK))
            dismiss()
        }

        return mBinding.root
    }

    @Parcelize
    class Args(
        val message: CharSequence,
        @DrawableRes val icon: Int,
        val cancelable:Boolean,
        val requestKey:String
    ) : Parcelable
}