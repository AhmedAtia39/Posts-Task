package sa.com.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import sa.com.data.local.AppDatabase
import sa.com.util.AppConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJson(): Json {
        val json = Json {
            //serializersModule = SerializersModule { contextual(Any::class, AnySerializer) }
            isLenient = true
            ignoreUnknownKeys = true
        }
        return json
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.DB_FILE_NAME)
            .build()
    }

}