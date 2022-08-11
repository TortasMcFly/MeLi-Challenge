package com.hector.melichallenge.di

import com.hector.melichallenge.data.remote.RemoteConstants
import com.hector.melichallenge.data.remote.MeLiApi
import com.hector.melichallenge.data.repository.ProductRepositoryImpl
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.use_case.SearchProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MeLiModule {

    @Provides
    @Singleton
    fun providesSearchProductUseCase(
        repository: ProductRepository
    ): SearchProductUseCase {
        return SearchProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesRepository(
        api: MeLiApi
    ): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesApi(
        okHttpClient: OkHttpClient
    ): MeLiApi {
        return Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeLiApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}