package org.mall.app.presentation.navigation

import androidx.compose.runtime.Composable
import org.mall.app.presentation.screen.setup.SetupScreen


sealed class WindowRoute(
    val title: String,
    val width: Int = 1920,
    val height: Int = 1080,
    val isFullScreen: Boolean = false,
    val content: @Composable () -> Unit = {}
) {
    data object SetupScreenRoute :
        WindowRoute("Setup", content = {
            SetupScreen()
        } , width = 1400 , height = 500)
}
