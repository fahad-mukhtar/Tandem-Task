package com.example.community.data.networkLayer

import com.example.community.data.models.CommunityResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


        private const val BASE_URL = "https://tandem2019.web.app/api/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        val okHttpClient : OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

        interface CommunityApiService {

            @GET("community_{page}.json")
            suspend fun getCommunity(@Path("page") int: Int): CommunityResponse
        }


        object CommunityApi {
            val retrofitService: CommunityApiService by lazy { retrofit.create(CommunityApiService::class.java) }
        }
