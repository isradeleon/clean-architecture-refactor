package com.techyourchance.architecture.common.dependency_injection

import android.app.Application
import androidx.room.Room
import com.techyourchance.architecture.BuildConfig
import com.techyourchance.architecture.common.database.MyRoomDatabase
import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            build()
        }

        return Retrofit.Builder()
            .baseUrl("http://api.stackexchange.com/2.3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun stackoverflowApi(retrofit: Retrofit): StackoverflowApi {
        return retrofit.create(StackoverflowApi::class.java)
    }

    @Provides
    @Singleton
    fun myRoomDatabase(application: Application): MyRoomDatabase {
        return Room.databaseBuilder(
            application,
            MyRoomDatabase::class.java,
            "MyDatabase"
        ).build()
    }

    @Provides
    fun favoriteQuestionDao(database: MyRoomDatabase): FavoriteQuestionDao {
        return database.favoriteQuestionDao
    }
}