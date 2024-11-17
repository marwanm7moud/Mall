package org.mall.app.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import java.awt.Dimension

actual object WindowManager {
    @Composable
    actual fun createNewWindow(
        title: String,
        onCloseRequest: () -> Unit,
        windowWidth: Int,
        windowHeight: Int,
        isFullScreen: Boolean,
        content: @Composable () -> Unit
    ) {
        val state: DialogState = rememberDialogState(
            position = WindowPosition(
                Alignment.TopStart
            ),
            size = if(isFullScreen) DpSize(1540.dp, 820.dp) else DpSize(windowWidth.dp, windowHeight.dp)
        )

        DialogWindow(
            onCloseRequest = onCloseRequest,
            title = title,
            //undecorated = true,
            //transparent = true,
            state = state,
            resizable = false,
            alwaysOnTop = false
        ) {
            if (!isFullScreen) {
                window.minimumSize = Dimension(windowWidth, windowHeight)
                window.size.setSize(windowWidth, windowHeight)
            } else {
                state.size = DpSize(1200.dp, 800.dp)
            }


            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    content()
                }
            }
        }
    }
}