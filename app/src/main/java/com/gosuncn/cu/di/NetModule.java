package com.gosuncn.cu.di;


import android.content.Context;

import com.gosuncn.cu.app.AppConfig;
import com.gosuncn.cu.net.TestService;
import com.gosuncn.cu.net.TestService2;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hwj on 2016/7/11.
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }
    /*   @Provides @Named("no_cached")
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {

        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                //.sslSocketFactory(getSSLConfig().getSocketFactory())
                .build();
        return httpClient;
    }*/


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Cache cache) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                //.sslSocketFactory(getSSLConfig().getSocketFactory())
                .build();
        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient httpClient, GsonConverterFactory gsonConverterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.NET_SERVER_IP)
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public TestService provideTestService(Retrofit retrofit) {
        return retrofit.create(TestService.class);
    }

    @Provides
    @Singleton
    public TestService2 provideTestService2(Retrofit retrofit) {
        return retrofit.create(TestService2.class);
    }

}
