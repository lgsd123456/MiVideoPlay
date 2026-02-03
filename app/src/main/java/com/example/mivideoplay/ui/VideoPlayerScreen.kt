package com.example.mivideoplay.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayerScreen(
    videoUri: android.net.Uri?,
    onPickVideo: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    
    var isFullScreen by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Handle URI changes
    LaunchedEffect(videoUri) {
        if (videoUri != null) {
            val mediaItem = MediaItem.fromUri(videoUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } else {
            // Placeholder video for testing (a public MP4 link) if nothing is selected
            val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = false // Don't auto-play placeholder
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { showControls = !showControls }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // Use our custom controls
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (showControls) {
            PlayerControls(
                player = exoPlayer,
                onFullScreenToggle = {
                    isFullScreen = !isFullScreen
                    activity?.let { act ->
                        val window = act.window
                        val controller = WindowCompat.getInsetsController(window, window.decorView)
                        if (isFullScreen) {
                            act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            controller.hide(WindowInsetsCompat.Type.systemBars())
                            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        } else {
                            act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                            controller.show(WindowInsetsCompat.Type.systemBars())
                        }
                    }
                },
                onPickVideo = onPickVideo
            )
        }
    }
}
