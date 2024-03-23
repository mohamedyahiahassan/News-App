package com.example.newsapp

 // we made it open so it is open for extension because it was final (we want to inherit from it)
open class Screen (val route:String, description:Int)

// description is of type int so we could use it in localisation as anything from R is an integer
class NewsScreen: Screen("news",R.string.news_app)

class CategoriesScreen: Screen("categories",R.string.categories)

class NewsArticleScreen: Screen("news_article", R.string.news_article)

class SettingsScreen: Screen("settings_screen", R.string.settings_screen)

class SearchScreen: Screen("search_screen", R.string.search_screen)