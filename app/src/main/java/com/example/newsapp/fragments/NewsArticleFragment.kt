package com.example.newsapp.fragments

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.R
import com.example.newsapp.api.APIManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsArticleFragment(title:String){

    var newsItem by  rememberSaveable {

        mutableStateOf<ArticlesItem?>(null)
    }

    val context = LocalContext.current

    LaunchedEffect(Unit){

        APIManager.getServices()
            .getSelectedArticle(Constants.API_Key,title,"title")
            .enqueue(object :Callback<ArticlesResponse>{
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {

                    if (response.isSuccessful) {

                        // articles is a list of articles
                     val chosenArticle =  response.body()?.articles?.get(0)
                        newsItem = chosenArticle




                    }
                }
                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }


        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(1f)
        ) {

            item {
                Column(modifier = Modifier.padding(top=20.dp)) {
                    GlideImage(
                        model = newsItem?.urlToImage,
                        contentDescription = "NewsArticle Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(1f),
                        loading = placeholder(painterResource(id = R.drawable.logo))
                    )
                Text(
                    text = newsItem?.source?.name ?: "", fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(
                    text = newsItem?.title ?: "", fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(
                    text = newsItem?.publishedAt ?: "", fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.End)
                )




                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)

                ) {
                    Text(
                        text = newsItem?.content ?: "",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    TextButton(modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.End),
                        onClick = {

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem?.url))
                            context.startActivity(intent)
                        }) {
                        Text(text = stringResource(R.string.view_full_article))

                    }
                }

            }

        }
        }


    }
