package com.example.firebaseauth.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FavoriteBook(
    val id: String,
    val title: String,
    val author: String,
    val year: String,
    val genre: String
)

object FavoritesManager {
    private val _favorites = mutableStateListOf<FavoriteBook>()
    val favorites: List<FavoriteBook> = _favorites

    private val _favoritesFlow = MutableStateFlow<List<FavoriteBook>>(_favorites.toList())
    val favoritesFlow: StateFlow<List<FavoriteBook>> = _favoritesFlow

    fun addFavorite(book: FavoriteBook) {
        if (!isBookFavorite(book.id)) {
            _favorites.add(book)
            _favoritesFlow.value = _favorites.toList()
        }
    }

    fun removeFavorite(bookId: String) {
        _favorites.removeIf { it.id == bookId }
        _favoritesFlow.value = _favorites.toList()
    }

    fun isBookFavorite(bookId: String): Boolean {
        return _favorites.any { it.id == bookId }
    }

    fun getFavoriteBooks(): List<FavoriteBook> {
        return _favorites.toList()
    }

    fun getFavoriteById(bookId: String): FavoriteBook? {
        return _favorites.find { it.id == bookId }
    }
}


@Composable
fun FavouritePage(
    onBookClick: (String) -> Unit = {}
) {
    val favorites by remember { mutableStateOf(FavoritesManager.favorites) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "FAVORITE BOOKS",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You don't have any favorite books yet",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(favorites) { book ->
                        FavoriteBookItem(
                            book = book,
                            onClick = { onBookClick(book.id) },
                            onRemove = { FavoritesManager.removeFavorite(book.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteBookItem(
    book: FavoriteBook,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp, 100.dp)
                    .background(Color(0xFFA7E8EB), shape = RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "${book.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "by ${book.author}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${book.year} • ${book.genre}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Remove button
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.HeartBroken,
                    contentDescription = "Remove from favorites",
                    tint = Color.Red
                )
            }
        }
    }
}