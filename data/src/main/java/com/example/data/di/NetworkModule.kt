package com.example.data.di


import com.example.data.repositoriesContracts.WebServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton


@Module
object NetworkModule {


    fun provideConverterFactory() : GsonConverterFactory{

        return GsonConverterFactory.create()
    }


@Provides
@Singleton
fun provideLoggingInterceptor() : HttpLoggingInterceptor{

    return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}

    @Provides
    @Singleton
    fun provideOkHttp(httpLoggingInterceptor : HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

@Provides
@Singleton
fun provideRetrofit(
    client : OkHttpClient,
    gsonConverterFactory: GsonConverterFactory

) : Retrofit {
    return Retrofit
        .Builder()
        .baseUrl("https://route-ecommerce.onrender.com/")
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()
}

@Provides
@Singleton
    fun getWebServices(retrofit: Retrofit) : WebServices{

        return retrofit.create(WebServices::class.java)

    }

}