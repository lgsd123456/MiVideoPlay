package com.example.mivideoplay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player

@Composable
fun PlayerControls(
    player: Player,
    onFullScreenToggle: () -> Unit,
    onPickVideo: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(player.isPlaying) }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        // Center Controls
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ControlButton(icon = Icons.Default.Replay10) {
                player.seekBack()
            }
            Spacer(modifier = Modifier.width(32.dp))
            ControlButton(
                icon = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                iconSize = 64.dp
            ) {
                if (isPlaying) player.pause() else player.play()
            }
            Spacer(modifier = Modifier.width(32.dp))
            ControlButton(icon = Icons.Default.Forward10) {
                player.seekForward()
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Seek Bar (Simple implementation for now)
            // TODO: Add actual Seek Bar logic

            Row {
                IconButton(onClick = onPickVideo) {
                    Icon(Icons.Default.Folder, contentDescription = "Select Video", tint = Color.White)
                }
                IconButton(onClick = onFullScreenToggle) {
                    Icon(Icons.Default.Fullscreen, contentDescription = "Full Screen", tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun ControlButton(
    icon: ImageVector,
    iconSize: androidx.compose.ui.unit.Dp = 48.dp,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = Modifier.size(iconSize)) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            tint = Color.White
        )
    }
}
