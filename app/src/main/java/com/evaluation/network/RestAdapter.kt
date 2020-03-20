package com.evaluation.network

import com.apollographql.apollo.ApolloClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class RestAdapter @Inject constructor() {

    private val BASE_URL = "https://countries.trevorblades.com/graphql"
    private var instance: ApolloClient

    init {
        instance = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(provideOkHttpClient()).build()
    }

    val restApiService: ApolloClient
        get() = instance

    private fun provideOkHttpClient(): OkHttpClient {
        val intLogging = HttpLoggingInterceptor()
        intLogging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(intLogging)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }
}