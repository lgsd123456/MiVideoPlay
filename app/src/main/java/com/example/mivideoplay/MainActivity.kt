package com.example.mivideoplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.mivideoplay.ui.VideoPlayerScreen
import com.example.mivideoplay.ui.theme.MiVideoPlayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var videoUri by remember { mutableStateOf<android.net.Uri?>(null) }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: android.net.Uri? ->
                videoUri = uri
            }

            MiVideoPlayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayerScreen(
                        videoUri = videoUri,
                        onPickVideo = { launcher.launch("video/*") }
                    )
                }
            }
        }
    }
}
