package com.gideon.dogify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gideon.dogify.ui.MainScreen
import org.koin.compose.viewmodel.koinViewModel
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen(viewModel = koinViewModel())
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}