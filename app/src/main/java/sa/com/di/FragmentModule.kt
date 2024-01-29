package sa.com.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import sa.com.ui.core.UIErrorHandler
import sa.com.ui.core.UIErrorHandlerImpl
import sa.com.ui.core.UILoadingHandler
import sa.com.ui.core.UILoadingHandlerImpl

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    @FragmentScoped
    fun provideUILoadingHandler(fragment: Fragment): UILoadingHandler {
        return UILoadingHandlerImpl(fragment)
    }

    @Provides
    @FragmentScoped
    fun provideUIErrorHandler(fragment: Fragment): UIErrorHandler {
        return UIErrorHandlerImpl(fragment)
    }
}