
package com.example.newsapp.utils


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.NewsArticleScreen
import com.example.newsapp.R
import com.example.newsapp.api.ArticlesItem


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(article:ArticlesItem,
             onCardClick:(title:String)->Unit,
             ){

    Card(onClick = {
                   // to apply unidirectional flow we wont import the nav controller
                   // we will send the click to the nav controller by callback using lambda
                   // navController.navigate("${NewsArticleScreen().route}/${article.title}")

        onCardClick(article.title?:"")

    },modifier = Modifier
        .fillMaxWidth(1f)
        .padding(8.dp)
        ,colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ){

        GlideImage(model = article.urlToImage?:"", contentDescription ="Article Image",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(1F),
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.logo)
        )
        }


        Text(text = article.source?.name?:"", modifier = Modifier.padding(8.dp))
        Text(text = article.title?:"", modifier = Modifier.padding(8.dp))
        Text(text = article.publishedAt?:"", modifier = Modifier
            .padding(8.dp)
            )


    }

