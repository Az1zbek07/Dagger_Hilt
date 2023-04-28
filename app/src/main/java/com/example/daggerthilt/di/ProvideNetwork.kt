package com.example.daggerthilt.di

import android.content.Context
import com.example.daggerthilt.database.NoteDao
import com.example.daggerthilt.network.ApiService
import com.example.daggerthilt.repository.NoteRepository
import com.example.daggerthilt.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [DatabaseProvider::class])
@InstallIn(SingletonComponent::class)
object ProvideNetwork {
    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService{
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper{
        return NetworkHelper(context)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(apiService: ApiService, dao: NoteDao): NoteRepository{
        return NoteRepository(dao, apiService)
    }
}