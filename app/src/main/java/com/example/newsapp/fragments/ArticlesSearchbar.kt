package com.example.newsapp.fragments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.api.APIManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import com.example.newsapp.ui.theme.green
import com.example.newsapp.utils.NewsCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesSearchBar( onReturnToNewsFragment:()->Unit,onCardClick:(title:String)->Unit) {

    var searchQuery by remember {

        mutableStateOf<String>("")
    }

    var isSearching by remember {

        mutableStateOf<Boolean>(false)
    }
    var newsListStates = remember {
        mutableStateListOf<ArticlesItem?>(null)
    }

    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = slideOutVertically()
    ) {

        Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp, bottomStart =
                        30.dp
                    )
                )
                .background(green)
                .padding(horizontal = 20.dp), contentAlignment = Alignment.Center
        ) {


            androidx.compose.material3.SearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onSearch = {
                    newsListStates = onImeACtionSearchClick(it, newsListStates)
                },
                active = isSearching,
                onActiveChange = {
                    isSearching = it
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search_bar_icon),
                        contentDescription = "search_icon"
                    )
                },
                trailingIcon = {
                    Image(painter = painterResource(id = R.drawable.close_search_icon),
                        contentDescription = "search_icon",
                        modifier = Modifier.clickable {
                            searchQuery = ""
                            isSearching = false
                            visible = false
                            onReturnToNewsFragment()
                        })

                },
                placeholder = {
                    Text(text = "Search Article", color = green)
                },
                colors = SearchBarDefaults.colors(containerColor = Color.White, dividerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 20.dp),
            ) {


            }
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
}

fun onImeACtionSearchClick(query: String, newsListStates: SnapshotStateList<ArticlesItem?>):SnapshotStateList<ArticlesItem?> {
    APIManager
        .getServices()
        .getSearchedArticle(Constants.API_Key,query)
        .enqueue(object :Callback<ArticlesResponse>{
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {

                val searchResultsList = response.body()?.articles
                if (searchResultsList != null) {
                    newsListStates.addAll(searchResultsList)


                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    return newsListStates

}

