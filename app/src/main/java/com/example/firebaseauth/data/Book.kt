package com.example.firebaseauth.data

data class Book(
    val title: String = "",
    val titleLower: String = "",
    val author: String = "",
    val type: String = "",
    val userId: String? = "", //ID utente
    val userEmail: String? = "" // Email dell'utente
)
