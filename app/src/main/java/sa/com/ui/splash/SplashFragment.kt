package sa.com.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sa.com.R
import sa.com.databinding.FragmentSplashBinding
import sa.com.ui.core.DataState
import sa.com.ui.core.UIErrorHandler
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    @Inject
    lateinit var mErrorHandler: UIErrorHandler

    private val mViewModel: SplashViewModel by viewModels()
    private lateinit var mBinding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSplashBinding.inflate(inflater,container,false)

        initObserving()
        return mBinding.root
    }

    private fun initObserving() {
        mViewModel.openHome.observe(viewLifecycleOwner) {
            if (it is DataState.Success )
                findNavController().navigate(R.id.open_home)
        }
    }
}