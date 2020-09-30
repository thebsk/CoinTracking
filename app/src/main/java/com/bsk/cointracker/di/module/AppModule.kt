package com.bsk.cointracker.di.module

import android.app.ActivityManager
import android.content.Context
import com.bsk.cointracker.data.remote.CoinService
import com.bsk.cointracker.data.remote.repository.CoinRemoteDataSource
import com.bsk.cointracker.di.qualifiers.CoroutineScopeIO
import com.bsk.cointracker.di.qualifiers.CryptoAPI
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideActivityManager(@ApplicationContext appContext: Context): ActivityManager {
        return appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }

    @Singleton
    @Provides
    fun provideCoinService(
        @CryptoAPI okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, CoinService::class.java)

    @Singleton
    @Provides
    fun provideCoinRemoteDataSource(coinService: CoinService) =
        CoinRemoteDataSource(
            coinService
        )

    @CryptoAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    @Singleton
    @Provides
    fun provideFireStore() = Firebase.firestore

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CoinService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }
}
