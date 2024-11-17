package org.mall.app

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import org.mall.app.presentation.screen.home.Configuration

import kotlinx.serialization.json.Json
import java.io.File

@Composable
actual fun VerticalScrollableBar(
    lazyListState: LazyListState,
    modifier: Modifier
) {
    VerticalScrollbar(
        rememberScrollbarAdapter(lazyListState),
        style = LocalScrollbarStyle.current.copy(
            unhoverColor = Color(0xff898989).copy(0.6f)
        ),
        modifier = modifier
    )
}



actual fun saveConfiguration(config: Configuration, filePath: String) {
    val jsonString = Json.encodeToString(Configuration.serializer(), config)
    File(filePath).writeText(jsonString)
}


actual fun loadConfiguration(filePath: String): Configuration? {
    val file = File(filePath)
    if (!file.exists()) return null
    val jsonString = file.readText()
    return Json.decodeFromString(Configuration.serializer(), jsonString)
}
