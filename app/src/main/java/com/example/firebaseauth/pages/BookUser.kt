package com.example.firebaseauth.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthViewModel

@Composable
fun BookUser(navController: NavController, authViewModel: AuthViewModel) {
    var expanded by remember { mutableStateOf(true) } // Start expanded by default

    Box(modifier = Modifier.fillMaxSize()) {
        // Overlay background when dropdown is expanded
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { expanded = false }
            )
        }

        // Position the dropdown menu in the center bottom of the screen
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp) // Adjust as needed to position above bottom nav
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(200.dp)
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate("library")
                    expanded = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Library",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Your Library")
                }

                Divider()

                DropdownMenuItem(onClick = {
                    navController.navigate("listBookAdd")
                    expanded = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Book",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Your Add Book")
                }
            }
        }
    }
}