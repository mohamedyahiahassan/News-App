package com.example.newsapp

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.fragments.CategoriesFragment
import com.example.newsapp.fragments.NewsArticleFragment
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.fragments.SettingsFragment
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.fragments.ArticlesSearchBar
import com.example.newsapp.utils.NavigationDrawerSheet
import com.example.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.launch
import java.util.Locale


class HomeActivity : ComponentActivity() {

val mContext = LocalContext

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {

                NewsContent()


            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsContent(){

    val mContext = LocalContext.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val toolbarTitle = remember{
        mutableIntStateOf(R.string.news_app)
    }
    var toolbarTitleInNews by rememberSaveable{
        mutableStateOf<String?>(null)
    }
    val actionIcon = remember {
        mutableStateOf<Boolean>(false)

    }
    var IsSearch by remember {

        mutableStateOf<Boolean>(false)
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { NavigationDrawerSheet(
                onSettingsClick = {

                    navController.popBackStack()
                    navController.navigate(SettingsScreen().route)
                    scope.launch {
                        drawerState.close()
                    }

                }, onCategoriesClick = {
                    // popBackStack Clears the back stack

                    navController.popBackStack()
                    if (navController.currentDestination?.route != CategoriesScreen().route) {
                        navController.navigate(CategoriesScreen().route)
                        scope.launch {
                            drawerState.close()
                        }

                    }
                    scope.launch {
                        drawerState.close()
                    }
                }

            )}
        },
    ) {
        Scaffold(
            topBar = {
                NewsTopAppBar(
                    toolbarTitle.value,
                    actionIcon.value,
                    toolbarTitleInNews,
                    IsSearch,
                onSideMenuClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()

                    }
                }
            },
                    onSearchBarIconClick = {
                        IsSearch = true
                navController.navigate(SearchScreen().route)

            })
                     },
        ) { paddingValues -> paddingValues

          //  NewsFragment(paddingValues = paddingValues, category = category)



            NavHost(
                navController = navController,
                startDestination = CategoriesScreen().route,
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ){

                composable(CategoriesScreen().route){
                   toolbarTitle.intValue = R.string.news_app
                    actionIcon.value = false
                    CategoriesFragment(navController)
                }
                composable("${NewsScreen().route}/{category_id}/{category_name}",
                    arguments = listOf(navArgument("category_id"){

                    type = NavType.StringType

                }, navArgument("category_name"){
                    type = NavType.IntType
                    }
                    )){
                    // navBackStack entry contains the arguments as parameters
                    val category = it.arguments?.getString("category_id")
                    val categoryName = it.arguments?.getInt("category_name")
                    toolbarTitle.intValue = categoryName ?: R.string.news_app
                    actionIcon.value = true
                    NewsFragment(category = category ?: "",{

                        navController.navigate("${NewsArticleScreen().route}/${it}")
                    })
                }
                composable(
                    route= "${NewsArticleScreen().route}/{title}",
                    arguments = listOf(navArgument("title"){
                        type = NavType.StringType
                    })



                ) {

                    actionIcon.value = false

                val title = it.arguments?.getString("title")
                    toolbarTitle.intValue = 0
                    toolbarTitleInNews = title?: ""
                    NewsArticleFragment(title?:"")


                }
                composable(route = SettingsScreen().route)
                {
                    SettingsFragment(){

                        // the nav controller has a context we used it to use android
                        // framework api to call the get SystemService etc...
                        navController.context.getSystemService(
                            LocaleManager::class.java
                        ).setApplicationLocales(LocaleList(Locale.forLanguageTag(it)))
                    }
                }

                composable(route = SearchScreen().route)
                {
                    BackHandler(true) {
                        IsSearch = false
                        navController.navigateUp()
                    }
                    ArticlesSearchBar(
                        onReturnToNewsFragment = {
                        IsSearch = false

                        navController.navigateUp()
                    },
                        onCardClick = {
                            IsSearch = false
                            navController.navigate("${NewsArticleScreen().route}/${it}")
                        })



                }



            }

        }


    }


}






