package org.mall.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.koin.core.context.startKoin
import org.mall.app.di.AppModule

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Maximized,
    )
    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "MallProject",
    ) {
        startKoin {
            modules(AppModule)
            createEagerInstances()
        }
        App()
    }
}