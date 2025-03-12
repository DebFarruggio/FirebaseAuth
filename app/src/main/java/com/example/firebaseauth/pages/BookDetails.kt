package com.example.firebaseauth.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookDetails(navController: NavController) {
    // State to control dialogs visibility
    var showContactDialog by remember { mutableStateOf(false) }
    var showLoanRequestDialog by remember { mutableStateOf(false) }
    var showReviewDialog by remember { mutableStateOf(false) }
    var isLoanRequested by remember { mutableStateOf(false) }

    // State for review text
    var reviewText by remember { mutableStateOf("") }
    var submittedReviews by remember { mutableStateOf<List<String>>(emptyList()) }

    // Coroutine scope for delayed actions
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // Back button in top right
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(30.dp, top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titolo
            Text(text = "TITLE", fontSize = 35.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            // Copertina e dettagli libro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(130.dp, 200.dp)
                        .background(Color((0xFFA7E8EB)), shape = RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(30.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    BookInfoItem(label = "AUTHORS", value = "Author Name")
                    BookInfoItem(label = "EDITION", value = "First Edition")
                    BookInfoItem(label = "YEAR", value = "2024")
                    BookInfoItem(label = "GENRE", value = "Fiction")
                    BookInfoItem(label = "CONDITION", value = "New")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Riassunto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "breve riassunto", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Pulsanti
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { showContactDialog = true },
                    colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))
                ) {
                    Text(text = "contact owner", color = Color.Black)
                }
                Button(
                    onClick = {
                        if (!isLoanRequested) {
                            showLoanRequestDialog = true
                            coroutineScope.launch {
                                delay(2000) // 2 seconds delay
                                showLoanRequestDialog = false
                                isLoanRequested = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        if (isLoanRequested) Color(0xFFFF0000) else Color(0xFF71F55E)
                    )
                ) {
                    Text(
                        text = if (isLoanRequested) "requested" else "available",
                        color = if (isLoanRequested) Color.White else Color.Black
                    )
                }
                Button(
                    onClick = { showReviewDialog = true },
                    colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))
                ) {
                    Text(text = "review", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Review section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                if (submittedReviews.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No reviews yet", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Reviews",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            submittedReviews.forEachIndexed { index, review ->
                                Text(
                                    text = review,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                if (index < submittedReviews.size - 1) {
                                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Contact Owner Dialog
        if (showContactDialog) {
            Dialog(onDismissRequest = { showContactDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Close button (X) in top left
                        IconButton(
                            onClick = { showContactDialog = false },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }

                        // Content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(top = 48.dp, bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Owner Contact Information",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            ContactInfoItem(label = "Name", value = "John Doe")
                            ContactInfoItem(label = "Email", value = "john.doe@example.com")
                            ContactInfoItem(label = "Phone", value = "+39 123 456 7890")

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { showContactDialog = false },
                                colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))
                            ) {
                                Text("Close", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }

        // Loan Request Confirmation Dialog
        if (showLoanRequestDialog) {
            Dialog(onDismissRequest = {  }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loan request sent",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Review Dialog
        if (showReviewDialog) {
            Dialog(onDismissRequest = { showReviewDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Close button (X) in top left
                        IconButton(
                            onClick = { showReviewDialog = false },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }

                        // Content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(top = 48.dp, bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Write a Review",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Text field for review input
                            OutlinedTextField(
                                value = reviewText,
                                onValueChange = { reviewText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                placeholder = { Text("Share your thoughts about this book...") },

                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {


                                Button(
                                    onClick = {
                                        if (reviewText.isNotBlank()) {
                                            submittedReviews = submittedReviews + reviewText
                                            reviewText = ""
                                            showReviewDialog = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))
                                ) {
                                    Text("Submit", color = Color.Black)
                                }






                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(60.dp)
        )
        Text(text = value)
    }
}

@Composable
fun BookInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Text(text = value)
    }
}