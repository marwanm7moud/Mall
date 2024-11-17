package org.mall.app.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun MallEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    backgroundColor: Color = Color.White,
    minLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isNumericOnly: Boolean = false,
    trailedIcon: @Composable (() -> Unit)? = null
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Red,
        backgroundColor = Color(0xff1A95D6).copy(alpha = 0.5f)
    )
    Box(
        modifier = modifier
            .height(minLines * 32.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(0.5.dp, Color(0xffE8E8E8), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(contentAlignment = Alignment.TopStart, modifier = Modifier.weight(1f)) {
                if (value == "") {
                    Text(
                        text = hint,
                        color = Color(0xFF8F8F8F),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.sp
                        )
                    )
                }
                CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                    BasicTextField(
                        modifier = Modifier.fillMaxSize(),
                        readOnly = readOnly,
                        enabled = enabled,
                        value = value,
                        onValueChange = {
                            if (isNumericOnly) {
                                if (it.all { it in '0'..'9' }) { // Only allow numeric characters
                                    onValueChange(it)
                                }
                            } else
                                onValueChange(it)
                        },
                        textStyle = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.sp
                        )
                    )
                }
            }
            if (trailedIcon != null) {
                trailedIcon()
            }
        }
    }
}

@Composable
fun MallEditTextWithTitle(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    backgroundColor: Color = Color(0xffE6F2FF)
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        MallEditText(value, onValueChange, Modifier, hint, backgroundColor)
    }
}