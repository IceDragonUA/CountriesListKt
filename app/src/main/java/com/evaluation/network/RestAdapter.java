package com.evaluation.network;

import com.apollographql.apollo.ApolloClient;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RestAdapter {

    private final String BASE_URL = "https://countries.trevorblades.com/graphql";

    private ApolloClient instance;

    public ApolloClient getRestApiService() {
        if (instance == null) {
            instance = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(provideOkHttpClient()).build();
        }
        return instance;
    }

    private static OkHttpClient provideOkHttpClient() {
        final HttpLoggingInterceptor intLogging = new HttpLoggingInterceptor();
        intLogging.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(intLogging)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
