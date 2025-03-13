package com.example.firebaseauth.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.firebaseauth.viewmodel.AuthViewModel
import com.example.firebaseauth.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    viewModel: SearchViewModel = viewModel()
) {
    var name by remember { mutableStateOf(TextFieldValue("Mario")) }
    var surname by remember { mutableStateOf(TextFieldValue("Rossi")) }
    var email by remember { mutableStateOf(TextFieldValue("mario.rossi@example.com")) }
    var isEditing by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    // Invece di usare hover, mostreremo sempre un leggero overlay con l'icona della fotocamera

    val context = LocalContext.current
    val plumColor = Color(0xFFDDA0DD)

    // Image picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // imageUri is already set before launching camera
        }
    }

    // Bottom sheet state for image selection
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
       //INSERIRE LA BARRA SOTTO CON HOME, SEARCH ETC..
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Main content box with plum border
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.85f)
                    .border(width = 2.dp, color = plumColor, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile image with camera icon
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable {
                                showBottomSheet = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Show image if available
                        imageUri?.let {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(context)
                                        .data(it)
                                        .build()
                                ),
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Semi-transparent overlay with camera icon
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(0.4f)
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Change Photo",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // White card for user info
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                if (isEditing) {
                                    OutlinedTextField(
                                        value = name,
                                        onValueChange = { name = it },
                                        label = { Text("Nome") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    )
                                    OutlinedTextField(
                                        value = surname,
                                        onValueChange = { surname = it },
                                        label = { Text("Cognome") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    )
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        label = { Text("Email") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    )
                                } else {
                                    Text(
                                        text = "Nome",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    Text(
                                        text = name.text,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                    Text(
                                        text = "Cognome",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    Text(
                                        text = surname.text,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                    Text(
                                        text = "Email",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    Text(
                                        text = email.text,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (isEditing) {
                                    Button(
                                        onClick = { isEditing = false },
                                        modifier = Modifier.padding(end = 8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2EBEC)),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Text("Salva", color = Color.Black)
                                    }
                                    Button(
                                        onClick = { isEditing = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2EBEC)),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Text("Annulla", color = Color.Black)
                                    }
                                } else {
                                    Button(
                                        onClick = { isEditing = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2EBEC)),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Text("Modifica", color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Bottom sheet for image selection
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Seleziona immagine", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            galleryLauncher.launch("image/*")
                            showBottomSheet = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2EBEC)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Galleria", color = Color.Black)
                    }
                    Button(
                        onClick = {
                            // Here you would need to create a temporary file for the camera image
                            // imageUri = createTempImageUri(context)
                            // cameraLauncher.launch(imageUri)
                            showBottomSheet = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2EBEC)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Fotocamera", color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}