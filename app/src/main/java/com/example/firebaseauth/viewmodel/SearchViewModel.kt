package com.example.firebaseauth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firebaseauth.data.Book
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SearchViewModel : ViewModel(){
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books


    fun searchBooks(query: String) {
        val lowerCaseQuery = query.trim().lowercase() // Converti la query in minuscolo

        FirebaseFirestore.getInstance().collection("books")
            .whereEqualTo("titleLower", lowerCaseQuery) // Cerca nel campo in minuscolo
            .get()
            .addOnSuccessListener { result ->
                val booksList = result.toObjects(Book::class.java)
                _books.value = booksList
            }
            .addOnFailureListener { exception ->
                Log.e("SearchViewModel", "Errore durante la ricerca", exception)
            }
    }
}

