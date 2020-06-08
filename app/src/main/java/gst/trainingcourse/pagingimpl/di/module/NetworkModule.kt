package gst.trainingcourse.pagingimpl.di.module

import dagger.Module
import dagger.Provides
import gst.trainingcourse.pagingimpl.BuildConfig
import gst.trainingcourse.pagingimpl.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
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
            .addQueryParameter("apiKey", "60329517165c4b60ae7de66692d985cb")
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }
}