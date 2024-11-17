package org.mall.app.presentation.navigation

import androidx.compose.runtime.Composable

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
    }
}