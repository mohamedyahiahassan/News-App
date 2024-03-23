package com.example.newsapp.model

import com.example.newsapp.api.SourcesResponse
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants.API_Key
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    //in this interface we put the parameters
    // we should send to the base url so the API knows the link to call

    @GET("v2/top-headlines/sources")
    fun getNewsSources(

        @Query ("apiKey") apiKey : String = API_Key,
        @Query ("category") category : String?
    ):Call<SourcesResponse>

    @GET("v2/everything")
    fun getEverything(
        @Query ("apiKey") apiKey : String = API_Key,
        @Query ("sources") sources : String = ""
    ) : Call<ArticlesResponse>

    @GET("v2/everything")
    fun getSelectedArticle(
        @Query ("apiKey") apiKey : String = API_Key,
        @Query ("q") q : String = "",
        @Query ("searchIn") searchInPlace : String = ""
    ): Call <ArticlesResponse>

    @GET("v2/everything")
    fun getSearchedArticle(
        @Query ("apiKey") apiKey : String = API_Key,
        @Query ("q") q : String = "",
    ): Call <ArticlesResponse>


}