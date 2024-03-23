package com.example.newsapp.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.api.APIManager
import com.example.newsapp.api.SourcesItem
import com.example.newsapp.api.SourcesResponse
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.ui.theme.green
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// I made a callback here using a lambda expression
// The NewsSourcesTabRow will receive a function (onTabSelected) as parameter
// this function needs a parameter (the sourceId : String) to be called
// so we will send the (the sourceId : String) parameter to the onTabSelected function from here
// so that the onTabSelected function can be called as lambda in the required place.


@Composable
fun NewsSourcesTabRow(category: String,onTabSelected:(sourceId: String)->Unit) {


    val selectedTabIndex = remember {

        mutableIntStateOf(0)
    }

    val sourcesList = remember {

        mutableStateListOf<SourcesItem?>()
    }


    //  when the parameter changes it calls the function again. it is used t
    // prevent side effects of composable functions.
    // it is only called once "like remember block" (not really)
    // API IS Called Here

    LaunchedEffect(Unit) {
        Log.e("Tag","Entered launched effeect in tab rows")
        APIManager.getServices()
            .getNewsSources(Constants.API_Key,category)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    val sources = response.body()?.sources
                    if (sources?.isNotEmpty() == true) {

                        sourcesList.addAll(sources)
                        Log.e("Tag","Entered onResponse")
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("Tag","API Call failed",t.cause)
                }

            })
    }

    //condition to make the the first tab selected automatically
    if (sourcesList.isNotEmpty()) {
        LaunchedEffect(Unit) {
            val sourceId = sourcesList.get(0)?.id
            onTabSelected(sourceId ?: "")
        }
    }


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex.intValue,
        edgePadding = 8.dp,
        indicator = {},
        divider = {},
    ) {


        sourcesList.forEachIndexed { index, item ->


            Tab(
                selected = index == selectedTabIndex.intValue,

                onClick = {

                    selectedTabIndex.intValue = index
                    onTabSelected((item?.id)?:"")

                },
                selectedContentColor = Color.White,
                unselectedContentColor = green
            ) {

                Text(
                    text = item?.name ?: "",
                    modifier = if (selectedTabIndex.intValue == index) {

                        Modifier
                            .padding(8.dp)
                            .background(green, RoundedCornerShape(50.dp))
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    } else {

                        Modifier
                            .padding(8.dp)
                            .border(2.dp, green, CircleShape)
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    }


                )
            }

        }
    }


}

