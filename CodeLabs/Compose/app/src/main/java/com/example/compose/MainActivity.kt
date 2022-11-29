package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    color = MaterialTheme.colors.primary
                ) {
                    MainBox()
                }
            }
        }
    }
}

@Composable
fun MainBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scrollable(state = rememberScrollState(),
                orientation = Orientation.Vertical)) {
                repeat(50) {
                    Greeting(name = "NO $it")
                }


            }
}

@Composable
fun Greeting(name: String) {
    Row() {
        Text(
            text = "Hello $name!", modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), color = Color.Blue
        )

        Button(onClick = { /*TODO*/ }) {
            Text(text = "SHOW")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        MainBox()
    }
}