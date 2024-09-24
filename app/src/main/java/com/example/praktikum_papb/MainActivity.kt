package com.example.praktikum_papb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum_papb.ui.theme.PraktikumPAPBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayTextFieldWithButton()
    }
}

@Composable
fun DisplayTextFieldWithButton() {
    var text by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Input Your Text") },
            modifier = Modifier
                .fillMaxWidth() // Make the text field fill the width of its parent
                .padding(horizontal = 16.dp) // Add horizontal padding for spacing
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(2.dp)
                ) // Add a border with rounded corners
        )
    }

    Column {
        Button(
            onClick = { Toast.makeText(context, text, Toast.LENGTH_LONG).show() },
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 6.dp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null // You can provide a content description for accessibility
            )

            Text(text = " Show Text")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PraktikumPAPBTheme {
        MainScreen()
    }
}
