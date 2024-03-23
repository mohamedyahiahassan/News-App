package com.example.newsapp.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.NewsScreen
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.ui.theme.blackText


@Composable
fun CategoriesFragment(navController:NavHostController){

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.pick_your_category), fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = blackText, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CategoriesList(navController)
    }

}

/*@Preview
@Composable
fun  CategoriesFragmentPreview(){

    CategoriesFragment()
}*/

@Composable
fun CategoriesList(navController:NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(Constants.categories.size) { position ->
            CategoryCard(
                category = Constants.categories.get(position),
                position,navController

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category, index: Int,navController:NavHostController) {
    val context = LocalContext.current
    Card(
        shape = if (index % 2 == 0) RoundedCornerShape(
            bottomStart = 24.dp,
            topEnd = 24.dp,
            topStart = 24.dp
        ) else
            RoundedCornerShape(bottomEnd = 24.dp, topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = category.backgroundColor),
        ),
        modifier = Modifier
            .padding(8.dp)
            .height(140.dp),
        onClick = {
                // click on card
            navController.navigate(route = "${NewsScreen().route}/${category.apiID}/${category.titleResID}")
        }
    ) {
        Image(
            painter = painterResource(id = category.drawableResId),
            contentDescription = stringResource(
                R.string.category_image
            ),

            modifier = Modifier
                .fillMaxHeight(0.75F)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop

        )
        Text(
            text = stringResource(id = category.titleResID),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )

    }
}