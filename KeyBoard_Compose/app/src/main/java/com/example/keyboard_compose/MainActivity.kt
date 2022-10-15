package com.example.keyboard_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.keyboard_compose.ui.theme.KeyBoard_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyBoard_ComposeTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Composable
fun SetWithText(message: String){
    Text(text = message, )
}

@Composable
fun SetImage()

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KeyBoard_ComposeTheme {
            setWithText(message = "BYE UNIVERSE?")
    }
}