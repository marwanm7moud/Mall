package org.mall.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mallproject.composeapp.generated.resources.Res
import mallproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mall.app.presentation.navigation.DefaultWindowManager
import org.mall.app.presentation.navigation.LocalWindowManager
import org.mall.app.presentation.screen.home.Configuration
import org.mall.app.presentation.screen.home.HomeScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val windowManager = remember { DefaultWindowManager() }
        CompositionLocalProvider(LocalWindowManager provides windowManager) {
            HomeScreen()
            windowManager.DisplayWindows()
        }
    }
}

@Composable
expect fun VerticalScrollableBar(lazyListState: LazyListState, modifier: Modifier = Modifier)

expect fun saveConfiguration(
    config: Configuration,
    filePath: String
)

expect fun loadConfiguration(filePath: String): Configuration?

const val configFilePath = "config.json"