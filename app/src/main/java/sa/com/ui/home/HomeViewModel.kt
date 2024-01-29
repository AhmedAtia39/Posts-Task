package sa.com.ui.home

import android.content.Context
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
import sa.com.data.local.LocalDataSource
import sa.com.data.local.posts.PostEntity
import sa.com.ui.core.DataState
import sa.com.ui.core.VMErrorHandler
import sa.com.ui.core.toErrorState
import javax.inject.Inject
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepo: MainRepo,
    private val errorHandler: VMErrorHandler,
    private val localDS: LocalDataSource,
) : ViewModel() {
    private val _posts: MutableLiveData<DataState<List<PostEntity>>> = MutableLiveData(DataState.Idle())
    val posts: LiveData<DataState<List<PostEntity>>> = _posts
    var networkStatus: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    private var postsJob: Job? = null
    var networkIsConnected = false

    init {
        getPosts()
    }

    private fun getPosts() {
        postsJob = viewModelScope.launch {
            if (mainRepo.getLocalPosts().isNotEmpty()){
                val  posts = mainRepo.getLocalPosts()
                _posts.value= DataState.Success(posts)
            }else if (!networkIsConnected){
                networkStatus.value = DataState.Success(false)
            } else{
                _posts.value = DataState.Loading()
                val result = mainRepo.getRemotePosts()
                _posts.value = when (result) {
                    is ApiResult.Success -> {
                        val posts = ArrayList<PostEntity>()
                        for (post in result.value){
                            posts.add(PostEntity(post.id,post.title, post.body))
                        }
                        mainRepo.savePosts(posts)
                        DataState.Success(posts)
                    }
                    is ApiResult.Failure -> {
                        val errorState = result.toErrorState()
                        if (errorState is DataState.Error) errorHandler.handleError(errorState)
                        errorState
                    }
                }
            }
        }
    }

    fun postsStateConsumed() {
        val state = _posts.value!!
        state.consumed = true
        _posts.value = state
    }

    fun isNetworkAvailable(context: Context) {
        networkIsConnected =  (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
    }
}
