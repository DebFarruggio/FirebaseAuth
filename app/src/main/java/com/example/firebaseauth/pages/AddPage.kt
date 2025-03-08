package com.example.firebaseauth.pages

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauth.data.Book
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddPage(modifier: Modifier = Modifier, navController: NavController, context: Context) {
    val title = remember {
        mutableStateOf("")
    }

    val author = remember {
        mutableStateOf("")
    }

    val type = remember {
        mutableStateOf("")
    }

    //var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    /*val mTypes = listOf("Adventure", "Classics", "Crime", "Folk", "Fantasy", "Historical", "Horror", "Literary fiction", "Mystery", "Poetry", "Plays",
            "Romance",
            "Science fiction",
            "Short stories",
            "Thrillers",
            "War", "Women’s fiction ", "Young adult")*/

    // Create a string value to store the selected city
    /*var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown*/

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add book", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title.value,

            // on below line we are adding on
            // value change for text field.
            onValueChange = { title.value = it },

            // on below line we are adding place holder
            // as text as "Enter your course name"
            placeholder = { Text(text = "Enter the title of the book") },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = author.value,
            onValueChange = {
                author.value = it
            },
            placeholder = { Text(text = "Enter the author of the book") },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = type.value,
            onValueChange = {
                type.value = it
            },
            placeholder = { Text(text = "Enter the type of the book") },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // on below line we are validating user input parameters.
                if (TextUtils.isEmpty(title.value.toString())) {
                    Toast.makeText(context, "Please enter title", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(author.value.toString())) {
                    Toast.makeText(context, "Please enter author", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(type.value.toString())) {
                    Toast.makeText(context, "Please enter type", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // on below line adding data to
                    // firebase firestore database.
                    addDataToFirebase(
                        title.value,
                        author.value,
                        type.value, context
                    )
                }
            },
            // on below line we are
            // adding modifier to our button.
        ) {
            // on below line we are adding text for our button
            Text(text = "Add Data")
        }
    }

}

fun addDataToFirebase(
    title: String,
    author: String,
    type: String,
    context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //creating a collection reference for our Firebase Firestore database.
    val dbCourses: CollectionReference = db.collection("books")
    val titleLower = title.lowercase()
    //adding our data to our courses object class.
    val books = Book(title, titleLower, author, type)

    //below method is use to add data to Firebase Firestore.
    dbCourses.add(books).addOnSuccessListener {
        // after the data addition is successful
        // we are displaying a success toast message.
        Toast.makeText(
            context,
            "Your book has been added to Firebase Firestore",
            Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "Fail to add book \n$e", Toast.LENGTH_SHORT).show()
    }

}
