package sa.com.ui.postDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sa.com.data.MainRepo
import sa.com.ui.core.DataState
import sa.com.ui.core.VMErrorHandler
import sa.com.ui.core.toErrorState
import javax.inject.Inject
import sa.com.data.remote.model.PostObject
import sa.com.ui.home.HomeFragment

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepo: MainRepo,
    private val errorHandler: VMErrorHandler,
) : ViewModel() {
    val postId: Int = savedStateHandle[HomeFragment.POST_ID]
        ?: throw IllegalArgumentException("${HomeFragment.POST_ID} is required")

    private val _post: MutableLiveData<DataState<PostObject>> = MutableLiveData(DataState.Idle())
    val post: LiveData<DataState<PostObject>> = _post
    private var postsJob: Job? = null

    init {
        getPost()
    }

    private fun getPost() {
        postsJob = viewModelScope.launch {
            _post.value = DataState.Loading()
            val result = mainRepo.getRemotePost(postId)
            _post.value = when (result) {
                is ApiResult.Success -> {
                    DataState.Success(result.value)
                }

                is ApiResult.Failure -> {
                    val errorState = result.toErrorState()
                    if (errorState is DataState.Error) errorHandler.handleError(errorState)
                    errorState
                }
            }
        }
    }

    fun postStateConsumed() {
        val state = _post.value!!
        state.consumed = true
        _post.value = state
    }
}
