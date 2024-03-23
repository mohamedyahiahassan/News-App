package com.example.newsapp.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.api.APIManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsSourcesTabRow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsFragment(
    category: String,
    onCardClick:(String)->Unit
) {

    val newsListStates = remember {
        mutableStateListOf<ArticlesItem?>(null)
    }

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        NewsSourcesTabRow(category = category) { sourceId ->

            APIManager
                .getServices()
                .getEverything(Constants.API_Key, sourceId)
                .enqueue(object : Callback<ArticlesResponse> {
                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                        newsListStates.clear()
                        val newsList = response.body()?.articles
                        if (newsList?.isNotEmpty() == true) {
                            newsListStates.addAll(newsList)
                        }
                    }

                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }

        LazyColumn() {
            items(newsListStates.size ?: 0) {
                newsListStates.toList()[it]?.let { it1 -> NewsCard(article = it1)
                {
                    onCardClick(it)
                } }
            }
        }


    }
}



