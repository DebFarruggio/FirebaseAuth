package com.example.firebaseauth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebaseauth.pages.AddPage
import com.example.firebaseauth.pages.BookDetails
import com.example.firebaseauth.pages.BookUser
import com.example.firebaseauth.pages.FavouritePage
import com.example.firebaseauth.pages.FilteredBooksPage
import com.example.firebaseauth.pages.HomePage
import com.example.firebaseauth.pages.LibraryPage
import com.example.firebaseauth.pages.ListBookAddPage
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
            SearchPage(searchViewModel, navController)
        }
        composable("profile"){
            ProfilePage(modifier, navController, authViewModel)
        }
        composable("favourite"){
            FavouritePage()
        }
        composable("book"){
            BookDetails(navController, authViewModel)
        }
        composable("bookUser"){
            BookUser(navController, authViewModel)
        }

        composable("library"){
            LibraryPage(navController, authViewModel)
        }

        composable("listBookAdd"){
            ListBookAddPage(navController)
        }



        composable("filteredbooks/{category}", arguments = listOf(navArgument("category") { type = NavType.StringType })){
            backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            Log.d("NavHost", "Navigato a filteredbooks con categoria: $category")
            // Passa category al ViewModel
            FilteredBooksPage(category, navController, searchViewModel)
        }
    }

}