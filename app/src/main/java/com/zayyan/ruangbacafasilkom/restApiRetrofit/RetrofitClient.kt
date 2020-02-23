package com.zayyan.ruangbacafasilkom.restApiRetrofit


import com.zayyan.ruangbacafasilkom.services.vinegere
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp

object RetrofitClient {

    val timestamp = Timestamp(System.currentTimeMillis())
    val getwaktu: String = timestamp.getTime().toString()
    val timenow: Int = getwaktu.substring(0, 10).toInt()
    private val APP_KEY = vinegere.vinegere("RuangBaca<~>"+timenow,null)

    private const val BASE_URL = "https://ruangbaca.techest.site/api/"


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("APP-KEY", APP_KEY)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}