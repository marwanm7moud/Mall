package org.mall.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.staticCompositionLocalOf


val LocalWindowManager = staticCompositionLocalOf<WindowNavigationManager> {
    error("No WindowManager provided")
}

interface WindowNavigationManager {
    fun openWindow(route: WindowRoute)

    fun closeWindow(route: WindowRoute)
}

class DefaultWindowManager : WindowNavigationManager {
    private val windows = mutableStateListOf<WindowRoute>()

    @Composable
    fun DisplayWindows() {
        windows.forEach { windowInfo ->
            WindowManager.createNewWindow(
                title = windowInfo.title,
                windowWidth = windowInfo.width,
                windowHeight = windowInfo.height,
                isFullScreen = windowInfo.isFullScreen,
                onCloseRequest = { closeWindow(windowInfo) },
                content = windowInfo.content
            )
        }
    }

    override fun openWindow(route: WindowRoute) {
        windows.add(route)
    }

    override fun closeWindow(route: WindowRoute) {
        windows.remove(route)
    }
}

