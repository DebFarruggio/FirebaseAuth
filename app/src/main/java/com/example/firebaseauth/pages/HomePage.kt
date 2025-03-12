package com.example.firebaseauth.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseauth.data.NavItem
import com.example.firebaseauth.viewmodel.AuthState
import com.example.firebaseauth.viewmodel.AuthViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseauth.viewmodel.SearchViewModel
import androidx.compose.material3.MaterialTheme
import  androidx.compose.foundation.layout.size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import  androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.firebaseauth.data.Book


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()

    var searchQuery by remember { mutableStateOf(query) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val searchFocusRequester = remember { FocusRequester() }

    var selectedGenre by remember { mutableStateOf<String?>(null) }

    val books = listOf("Book 1", "Book 2", "Book 3", "Book 4")

    val categories = listOf(
        "Adventure", "Classics", "Crime", "Folk", "Fantasy", "Historical",
        "Horror", "Literary fiction", "Mystery", "Poetry", "Plays", "Romance",
        "Science fiction", "Short stories", "Thrillers", "War", "Women’s fiction", "Young adult"
    )


    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Search", Icons.Default.Search),
        NavItem("Add", Icons.Default.Add),
        NavItem("Favourite", Icons.Default.Favorite),
        NavItem("Book", Icons.Default.Book)
    )


    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }


    LaunchedEffect(Unit) {
        searchFocusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(88.dp))

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }

                // Campo di ricerca
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .focusRequester(searchFocusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        },
                    placeholder = { Text("Search", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFA7E8EB),
                        unfocusedContainerColor = Color(0xFFA7E8EB),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            Log.d("SearchPage", "Ricerca avviata con query: $query")
                            viewModel.searchBooks(query)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )

                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.Black
                    )
                }
            }

            //CONTAINER PAGINA
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                // GENRE

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Book,
                        contentDescription = "Book",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GENRE", fontWeight = FontWeight.Bold)
                }

                Row(

                    modifier = Modifier.horizontalScroll(rememberScrollState()), ) {
                    categories.forEach { category ->
                        var isSelected by remember { mutableStateOf(false) }
                        val animatedAlpha by animateFloatAsState(
                            targetValue = if (isSelected) 1f else 0.5f,
                            animationSpec = tween(durationMillis = 200), label = "alphaAnimation"
                        )

                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .background(
                                    Color(0xFFA7E8EB).copy(alpha = animatedAlpha),
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    isSelected = !isSelected
                                    selectedGenre = if (isSelected) category else null
                                    if (category.isNotEmpty()) {
                                        navController.navigate("filteredbooks/$category")
                                    }

                                }
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        )

                        {
                            Text(category, color = Color.Black)
                        }
                    }
                }
                Text(
                    text = "see all",
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("search")
                        }
                        .padding(8.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                // FROM LIBRARY
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(Color.White)
                        .padding(8.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Book,
                            contentDescription = "Book",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Testo
                        Text(
                            "FROM YOUR LIBRARY",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Row(modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 40.dp)
                    ) {
                        books.forEach { book ->
                            var isSelected by remember { mutableStateOf(false) }
                            val animatedAlpha by animateFloatAsState(
                                targetValue = if (isSelected) 1f else 0.5f,
                                animationSpec = tween(durationMillis = 200), label = "alphaAnimation"
                            )
                            Spacer(modifier = Modifier.width(10.dp)) //spazio tra i libri

                            Box(
                                modifier = Modifier
                                    .height(140.dp)
                                    .width(120.dp)
                                    .background(
                                        Color(0xFFA7E8EB).copy(alpha = animatedAlpha),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        isSelected = !isSelected
                                        selectedGenre = if (isSelected) book else null

                                            navController.navigate("book")

                                    }
                                    .padding(12.dp),


                                contentAlignment = Alignment.Center
                            )

                            {
                                Text(book, color = Color.Black)
                            }

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                // FAVOURITE
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(Color.White)
                        .padding(8.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favourite",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Testo
                        Text(
                            "YOUR FAVOURITE",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Row(modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 40.dp)
                    ) {
                        books.forEach { book ->
                            var isSelected by remember { mutableStateOf(false) }
                            val animatedAlpha by animateFloatAsState(
                                targetValue = if (isSelected) 1f else 0.5f,
                                animationSpec = tween(durationMillis = 200), label = "alphaAnimation"
                            )
                            Spacer(modifier = Modifier.width(10.dp)) //spazio tra i libri

                            Box(
                                modifier = Modifier
                                    .height(140.dp)
                                    .width(120.dp)
                                    .background(
                                        Color(0xFFA7E8EB).copy(alpha = animatedAlpha),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        isSelected = !isSelected
                                        selectedGenre = if (isSelected) book else null
                                    }
                                    .padding(12.dp),


                                contentAlignment = Alignment.Center
                            )

                            {
                                Text(book, color = Color.Black)
                            }

                        }
                    }
                }

            }
        }
    }
}





