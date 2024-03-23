package com.example.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.blackText
import com.example.newsapp.ui.theme.green
import com.example.newsapp.ui.theme.poppinsFamily


@Composable
fun NavigationDrawerSheet(onSettingsClick:()->Unit,onCategoriesClick:()->Unit){

    ModalDrawerSheet (modifier = Modifier.fillMaxWidth(0.8F)) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.pattern),
                    contentScale = ContentScale.FillBounds
                )
        ) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.15f)
                    .background(green)
            ) {

                Text(
                    text = stringResource(id = R.string.news_app),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = poppinsFamily,
                )
            }

            NavigationDrawerItem(R.drawable.categories_icon, R.string.categories,onCategoriesClick)
            NavigationDrawerItem(R.drawable.settings_icon, R.string.settings,onSettingsClick)

        }
    }
}

@Composable
fun NavigationDrawerItem(iconResId:Int,textResId:Int, onNavigationItemClick: () -> Unit){

    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp)
            .clickable {
                onNavigationItemClick()
            }
    ){
        Image(painter = painterResource(id = iconResId),
            contentDescription = "categories icon",
            modifier = Modifier.width(24.dp)
        )
        Text(text = stringResource(textResId),
            color = blackText,
            fontSize = 24.sp,
            fontFamily = poppinsFamily,
            modifier = Modifier.padding(start = 10.dp)
        )
    }


}