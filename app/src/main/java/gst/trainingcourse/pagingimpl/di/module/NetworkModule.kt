package gst.trainingcourse.pagingimpl.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gst.trainingcourse.pagingimpl.API_KEY
import gst.trainingcourse.pagingimpl.BuildConfig
import gst.trainingcourse.pagingimpl.MainApplication
import gst.trainingcourse.pagingimpl.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun create(): ApiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .client(buildClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private fun buildClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(buildInterceptor())
        .addInterceptor(buildLogging())
        .build()

    private fun buildLogging(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun buildInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }
}