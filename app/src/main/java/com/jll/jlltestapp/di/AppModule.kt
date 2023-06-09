package com.jll.jlltestapp.di

import androidx.paging.ExperimentalPagingApi
import com.jll.jlltestapp.BuildConfig
import com.jll.jlltestapp.data.remote.APIEndPoint
import com.jll.jlltestapp.data.repository.ProductRepositoryImpl
import com.jll.jlltestapp.domain.repository.ProductRepository
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProductUseCase(repository: ProductRepository): ProductUseCase = ProductUseCase(repository)

    @Singleton
    @Provides
    @ExperimentalPagingApi
    fun provideProductRepository(api: APIEndPoint): ProductRepository = ProductRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideApi(): APIEndPoint =
        Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(provideOkhttpClient()).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(FlowCallAdapterFactory())
            .build().create(APIEndPoint::class.java)


    @Provides
    fun provideOkhttpClient(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(getInterceptor())

        httpClient.interceptors().add(Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response
        })

        return httpClient.addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .build()
            request = request.newBuilder()
                .url(url).build()
            chain.proceed(request)
        }
    }

}