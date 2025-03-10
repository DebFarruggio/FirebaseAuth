package com.example.firebaseauth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.pages.AddPage
import com.example.firebaseauth.pages.FavouritePage
import com.example.firebaseauth.pages.HomePage
import com.example.firebaseauth.pages.LoginPage
import com.example.firebaseauth.pages.ProfilePage
import com.example.firebaseauth.pages.SearchPage
import com.example.firebaseauth.pages.SignupPage
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.firebaseauth.viewmodel.SearchViewModel

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, searchViewModel: SearchViewModel){
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    NavHost(navController = navController, startDestination = "login") {
        composable("login"){
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup"){
            SignupPage(modifier, navController, authViewModel)
        }
        composable("main"){
            MainPage(modifier, navController, authViewModel, searchViewModel)
        }
        composable("home"){
            HomePage(modifier, navController, authViewModel)
        }
        composable("addpage"){
            AddPage(modifier, navController, context)
        }
        composable("search"){
            SearchPage(searchViewModel)
        }
        composable("profile"){
            ProfilePage()
        }
        composable("favourite"){
            FavouritePage()
        }
    }

}