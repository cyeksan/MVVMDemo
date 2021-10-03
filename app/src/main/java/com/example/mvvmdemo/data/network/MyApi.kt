package com.example.mvvmdemo.data.network

import com.example.mvvmdemo.data.network.responses.AuthResponse
import com.example.mvvmdemo.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyApi {
    @Headers("Content-Type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String

    ): Response<AuthResponse>

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi{

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.107:45455/api/" )
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }

    }

}