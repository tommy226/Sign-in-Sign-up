package com.sungbin.sign_in_sign_up.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sungbin.sign_in_sign_up.MyApplication
import com.sungbin.sign_in_sign_up.utils.SSLFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val gson = GsonBuilder().setLenient().create()
//        private const val BASE_URL = "http://192.168.1.29:8082/"
private const val BASE_URL = "https://vlaos-smartwork.com/api/"

        fun create(): NetworkAPI{
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor{
                val request = it.request()
                    .newBuilder()
                    .addHeader("content-type", "application/json")
//                    .addHeader("X-AUTH-TOKEN", MyApplication.prefs.getString("access",""))       // access
//                    .addHeader("Authorization",MyApplication.prefs.getString("refresh",""))      // refresh
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
//
//            val client = SSLFactory().unsafeOkHttpClient        // SSL 인증서 에러 시 사용 (http 로컬 통신)
//                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(headerInterceptor)
//                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(NetworkAPI::class.java)
        }
    }
}