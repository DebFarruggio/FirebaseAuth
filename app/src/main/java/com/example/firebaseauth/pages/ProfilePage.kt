package com.example.firebaseauth.pages


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.firebaseauth.viewmodel.SearchViewModel
import androidx.compose.material3.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, viewModel: SearchViewModel = viewModel()) {

    var name by remember { mutableStateOf(TextFieldValue("Mario")) }
    var surname by remember { mutableStateOf(TextFieldValue("Rossi")) }
    var isEditing by remember { mutableStateOf(false) }

    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(30.dp, top = 30.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }

        // BOX CONTENUTO
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nome") }
                    )
                    OutlinedTextField(
                        value = surname,
                        onValueChange = { surname = it },
                        label = { Text("Cognome") }
                    )
                } else {
                    Text(text = "Nome: ${name.text}")
                    Text(text = "Cognome: ${surname.text}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    if (isEditing) {
                        Button(onClick = { isEditing = false }) { Text("Salva") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { isEditing = false }) { Text("Annulla") }
                    } else {
                        Button(onClick = { isEditing = true }) { Text("Modifica") }
                    }
                }
            }
        }
    }