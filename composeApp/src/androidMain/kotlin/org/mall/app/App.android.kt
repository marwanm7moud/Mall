package org.mall.app

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.mall.app.presentation.screen.home.Configuration

@Composable
actual fun VerticalScrollableBar(
    lazyListState: LazyListState,
    modifier: Modifier
) {
}

actual fun saveConfiguration(
    config: Configuration,
    filePath: String
) {
}

actual fun loadConfiguration(filePath: String): Configuration? {
    TODO("Not yet implemented")
}