package sa.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import sa.com.data.MainRepo
import sa.com.ui.core.VMErrorHandler
import sa.com.ui.core.VMErrorHandlerImpl

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideVMErrorHandler(mainRepo: MainRepo): VMErrorHandler {
        return VMErrorHandlerImpl(mainRepo)
    }

}