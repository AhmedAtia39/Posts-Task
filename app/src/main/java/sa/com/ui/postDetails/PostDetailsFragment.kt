package sa.com.ui.postDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import sa.com.R
import sa.com.data.remote.model.PostObject
import sa.com.databinding.FragmentPostDetailsBinding
import sa.com.ui.core.DataState
import sa.com.ui.core.UIErrorHandler
import sa.com.ui.core.UILoadingHandler
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    companion object {
     fun newInstance() = PostDetailsFragment()
    }

    @Inject
    lateinit var mErrorHandler: UIErrorHandler
    @Inject
    lateinit var mLoadingHandler: UILoadingHandler

    private val mViewModel: PostDetailsViewModel by viewModels()
    private lateinit var mBinding: FragmentPostDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        initAppBar()
        initPost()

        return mBinding.root
    }

    private fun initPost() {
        mViewModel.post.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> updatePostState(true, null, null, it)
                is DataState.Success -> updatePostState(false, null, it.data, it)
                is DataState.Empty -> updatePostState(false, getString(R.string.msg_no_result), null, it)
                is DataState.Error -> updatePostState(false, null, null, it)
                else -> updatePostState(false, null, null, it)
            }
        }
    }

    private fun updatePostState(
        loading: Boolean,
        msg:String?,
        data: PostObject?,
        dataState: DataState<PostObject>
    ) {
        if (loading)
            mBinding.pbPost.show()
         else
            mBinding.pbPost.hide()

        mBinding.title.text = data?.title
        mBinding.postBody.text = data?.body
        mBinding.postMsg.text = msg ?: ""
        mBinding.postMsg.isVisible = msg != null
        if (!dataState.consumed) {
            if (dataState is DataState.Error) {
                mErrorHandler.handleError(dataState)
            }
            mViewModel.postStateConsumed()
        }
    }

    private fun initAppBar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mBinding.topAppBar.setupWithNavController(navController, appBarConfiguration)
    }
}