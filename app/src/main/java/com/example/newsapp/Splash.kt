package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme


class Splash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                SplashContent()
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@Splash, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashContent(){


Column (modifier = Modifier
    .fillMaxSize()
    .paint(painter = painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop),
    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
    Spacer(modifier = Modifier.weight(1F))
    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "splash logo",
        modifier = Modifier.fillMaxWidth(0.7F), contentScale = ContentScale.FillWidth)
    Spacer(modifier = Modifier.weight(0.3F))
    Image(painter = painterResource(id = R.drawable.route_watermark), contentDescription = "watermark",
        modifier = Modifier.size(200.dp))
}
}
