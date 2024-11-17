package org.mall.app.presentation.navigation

import androidx.compose.runtime.Composable

expect object WindowManager {
    @Composable
    fun createNewWindow(
        title: String,
        onCloseRequest: () -> Unit,
        windowWidth: Int = 1200,
        windowHeight: Int = 800,
        isFullScreen: Boolean = false,
        content: @Composable () -> Unit
    )
}