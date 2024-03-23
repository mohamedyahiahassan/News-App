package com.example.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.fragments.ArticlesSearchBar
import com.example.newsapp.ui.theme.green
import com.example.newsapp.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(appBarTitleResource:Int,
                  actionIcon:Boolean,
                  appBarTitleInNews:String?=null,
                  IsSearch : Boolean,
                  onSideMenuClick : () -> Unit,
                  onSearchBarIconClick:()->Unit
                  )

                {


    if (IsSearch==false){
    CenterAlignedTopAppBar(

        navigationIcon = {
        Image (painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "Navigation Drawer Icon",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onSideMenuClick()
                })
                         },
        title = { Text(
            text = if(appBarTitleResource!=0) {
                stringResource(appBarTitleResource)
            }else{
                appBarTitleInNews?:""
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = green,
            titleContentColor = white
        ),
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomStart = 25.dp,
                bottomEnd = 25.dp)
        ),
        actions = {
            if (actionIcon) {
                IconButton(onClick = {
                    // click of Search icon
                    onSearchBarIconClick()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "Icon Search"
                    )

                }
            }
        }
    )}



}



