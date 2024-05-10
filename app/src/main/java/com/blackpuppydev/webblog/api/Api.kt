package com.blackpuppydev.webblog.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    companion object{

        fun getRetrofit() : Retrofit {
            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
//            .client(getOkHttpClient())
            return builder.build()
        }


        fun getOkHttpClient(): OkHttpClient? {
            return null
        }


    }




}