package com.example.newsapp.api

import com.example.newsapp.model.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIManager {

    private val retrofit : Retrofit

    init {

        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getServices():NewsService{

        return retrofit.create(NewsService::class.java)
    }


}