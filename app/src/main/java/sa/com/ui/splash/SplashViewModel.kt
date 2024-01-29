package sa.com.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sa.com.data.MainRepo
import sa.com.ui.core.DataState
import sa.com.ui.core.VMErrorHandler
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepo: MainRepo,
    private val errorHandler: VMErrorHandler,
) : ViewModel() {

    private val _openHome = MutableLiveData<DataState<Boolean>>(DataState.Idle())
    val openHome: LiveData<DataState<Boolean>> = _openHome

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _openHome.value = DataState.Loading()
            delay(3000)
            _openHome.value = DataState.Success(true)
        }
    }
}