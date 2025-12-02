package com.gideon.dogify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gideon.dogify.api.model.Breed
import com.gideon.dogify.usecase.FetchBreedsUseCase
import com.gideon.dogify.usecase.GetBreedsUseCase
import com.gideon.dogify.usecase.ToggleFavouriteStateUseCase

class MainActivity : ComponentActivity() {

    suspend fun greet() =
        "${FetchBreedsUseCase().invoke()}\n" +
                "${GetBreedsUseCase().invoke()}\n" +
                "${
                    ToggleFavouriteStateUseCase().invoke(
                        Breed(
                            "toggle favourite state test", ""
                        )
                    )
                }"

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            // This is your app's theme wrapper
            MaterialTheme {
                // Surface provides background color
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingScreen()
                }
            }
        }
    }

    @Composable
    fun GreetingScreen() {
        // State holds the text value and triggers recomposition when it changes
        var greetingText by remember { mutableStateOf("Loading...") }

        // LaunchedEffect runs the suspend function when the composable is first created
        LaunchedEffect(Unit) {
            greetingText = greet()
        }

        // UI Layout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = greetingText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}