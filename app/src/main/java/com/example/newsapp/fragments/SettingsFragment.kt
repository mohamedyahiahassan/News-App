package com.example.newsapp.fragments


import android.app.LocaleManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.example.newsapp.HomeActivity
import com.example.newsapp.R
import com.example.newsapp.fragments.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.green


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsFragment(onChangeLocale:(locale:String)->Unit) {

    // we made this context as it is the context of home activity (we brought it here)
    val activity = (LocalContext.current) as HomeActivity

    // get you system language
    // val systemLanguage = locale.current.language

    // this variable holds the language of the app currently
    var currentLocale = activity.resources.configuration.locales.get(0)

    var isExpanded by remember {

        mutableStateOf<Boolean>(false)
    }

    var languageChosen by remember {

        if (currentLocale.toString() =="en_US"){
            mutableStateOf<String>("English")
        }else if (currentLocale.toString().equals("ar_EG") ){
            mutableStateOf<String>("عربي")
        } else {
            mutableStateOf<String>("test")
        }


    }




    Column(modifier = Modifier.padding(25.dp)) {
        Text(text = "Language", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        ExposedDropdownMenuBox(modifier = Modifier.padding(20.dp),
            expanded = isExpanded, onExpandedChange = {

            isExpanded = it
            // it is the a value that changes automatically based on the drop down status
            // (opened or closed). the system returns this value

        }) {

            OutlinedTextField(
                value = languageChosen,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.dropdown_icon),
                        contentDescription = ""
                    )
                },
                placeholder = {
                    Text(text = stringResource(R.string.please_choose_preferred_language))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded, onDismissRequest = { isExpanded = false }
            ) {

                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        isExpanded = false
                        languageChosen = "English"
                        onChangeLocale("en-US")
                        activity.finish()
                        activity.startActivity(activity.intent)

                    })
                DropdownMenuItem(
                    text = { Text("عربي") },
                    onClick = {
                        isExpanded = false
                        languageChosen = "عربي"

                        onChangeLocale("ar-EG")
                        activity.finish()
                        activity.startActivity(activity.intent)

                        Log.e("Tag",currentLocale.toString())





                    })


            }
        }

    }

}
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }


    @Composable
    fun GreetingPreview() {
        NewsAppTheme {
            Greeting("Android")
        }
    }
