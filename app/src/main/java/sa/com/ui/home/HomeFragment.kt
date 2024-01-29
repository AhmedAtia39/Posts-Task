package sa.com.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sa.com.R
import sa.com.data.local.posts.PostEntity
import sa.com.databinding.FragmentHomeBinding
import sa.com.ui.core.*
import sa.com.ui.custom.MessageDialog
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
      const  val POST_ID = "POST_ID"
     fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var mErrorHandler: UIErrorHandler
    @Inject
    lateinit var mLoadingHandler: UILoadingHandler

    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mPostsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        initNetwork()
        initPosts()

        return mBinding.root
    }

    private fun initNetwork() {
        mViewModel.isNetworkAvailable(requireContext())
        mViewModel.networkStatus.observe(viewLifecycleOwner){
            if (it.successOrNull()?.data == false)
                networkError()
        }
    }

    private fun initPosts() {
        mPostsAdapter = PostsAdapter {
            mViewModel.isNetworkAvailable(requireContext())
            if (mViewModel.networkIsConnected)
                findNavController().navigate(R.id.open_post_details , bundleOf(POST_ID to it.post_id))
            else
                networkError()
        }

        mBinding.rvPosts.adapter = mPostsAdapter
        mViewModel.posts.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> updatePostsState(true, null, null, it)
                is DataState.Success -> updatePostsState(false, null, it.data, it)
                is DataState.Empty -> updatePostsState(false, getString(R.string.msg_no_result), null, it)
                is DataState.Error -> updatePostsState(false, null, null, it)
                else -> updatePostsState(false, null, null, it)
            }
        }
    }

    private fun networkError() {
        MessageDialog.newInstance(getString(R.string.connect_network_please), R.drawable.ic_info_msg)
            .show(childFragmentManager, MessageDialog.TAG)
    }

    private fun updatePostsState(
        loading: Boolean,
        msg:String?,
        data: List<PostEntity?>?,
        dataState: DataState<List<PostEntity>>
    ) {
        if (loading)
            mBinding.pbPosts.show()
         else
            mBinding.pbPosts.hide()

        mBinding.postsMsg.text = msg ?: ""
        mBinding.postsMsg.isVisible = msg != null
        mPostsAdapter.submitList(data?: emptyList())
        if (!dataState.consumed) {
            if (dataState is DataState.Error) {
                mErrorHandler.handleError(dataState)
            }
            mViewModel.postsStateConsumed()
        }
    }
}