package sa.com.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import sa.com.BuildConfig
import sa.com.data.local.LocalDataSource
import sa.com.data.remote.PostsApi
import sa.com.util.AppConstants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        localDS: LocalDataSource,
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
//                    .addHeader("token", localDS.getUserToken()?:"")
                    .build()
                chain.proceed(request)
            }
            //refresh the token every request
            .addInterceptor{ chain ->
                val result = chain.proceed(chain.request())
//                val token = result.header("token", "")
//                if (!token.isNullOrEmpty()) {
//                    localDS.setUserToken(token)
//                }
                result
            }
            .addInterceptor(interceptor)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient,
        json: Json
    ): PostsApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .build().create(PostsApi::class.java)
    }
}