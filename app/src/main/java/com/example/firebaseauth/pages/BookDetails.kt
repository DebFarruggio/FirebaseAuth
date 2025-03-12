package com.example.firebaseauth.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BookDetails(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Back button in top right
        IconButton(
            onClick =  {navController.popBackStack()},
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
                        .size(130.dp, 180.dp)
                        .background(Color((0xFFA7E8EB)), shape = RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(30.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "AUTHORS", fontWeight = FontWeight.Bold)
                    Text(text = "EDITION", fontWeight = FontWeight.Bold)
                    Text(text = "YEAR", fontWeight = FontWeight.Bold)
                    Text(text = "GENRE", fontWeight = FontWeight.Bold)
                    Text(text = "CONDITION", fontWeight = FontWeight.Bold)
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
            )
            {
                Text(text = "breve riassunto", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Pulsanti
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))) {
                    Text(text = "contact owner", color = Color.Black)
                }
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color(0xFF71F55E))) {
                    Text(text = "available", color = Color.Black)
                }
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color(0xFFA7E8EB))) {
                    Text(text = "review", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Review section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "review", fontWeight = FontWeight.Bold)
            }


        }
    }
}