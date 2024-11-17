package org.mall.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MallScaffold(
    isLoading: Boolean,
    showError: Boolean,
    errorMessage: String,
    onDismissError: () -> Unit,
    content: @Composable () -> Unit
) {
    FadeAnimation(visible = showError) {
        MallErrorDialog(
            title = "ERROR",
            text = errorMessage,
            onDismissRequest = onDismissError,
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun MallErrorDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(16.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    val density = LocalDensity.current.density
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier.fillMaxWidth(density / 1.5f)
                .heightIn(min = 200.dp)
                .background(containerColor, shape),
            horizontalAlignment = horizontalAlignment
        ) {
            Icon(
                imageVector = IcError(),
                contentDescription = "ERROR",
                tint = Color.Red,
                modifier = Modifier.size(64.dp).padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                title,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h2
            )
            Text(
                text,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.body1
            )
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.height(52.dp)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(LocalDensity.current.density / 2f),
            ) {
                Text(
                    "OK",
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

fun IcError() =
    ImageVector.Builder(
        name = "IcError", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
        viewportWidth = 512.0f, viewportHeight = 512.0f,
    ).apply {
        path(
            fill = SolidColor(Color.Red),
            stroke = SolidColor(Color.Red),
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(256.0f, 42.667f)
            curveTo(373.61f, 42.667f, 469.333f, 138.39f, 469.333f, 256.0f)
            curveTo(469.333f, 373.61f, 373.61f, 469.333f, 256.0f, 469.333f)
            curveTo(138.39f, 469.333f, 42.667f, 373.61f, 42.667f, 256.0f)
            curveTo(42.667f, 138.39f, 138.39f, 42.667f, 256.0f, 42.667f)
            close()
            moveTo(256.0f, 85.333f)
            curveTo(161.541f, 85.333f, 85.333f, 161.541f, 85.333f, 256.0f)
            curveTo(85.333f, 350.459f, 161.541f, 426.667f, 256.0f, 426.667f)
            curveTo(350.459f, 426.667f, 426.667f, 350.459f, 426.667f, 256.0f)
            curveTo(426.667f, 161.541f, 350.459f, 85.333f, 256.0f, 85.333f)
            close()
            moveTo(256.0f, 314.709f)
            curveTo(271.238f, 314.709f, 282.667f, 325.973f, 282.667f, 341.333f)
            curveTo(282.667f, 356.693f, 271.238f, 367.957f, 256.0f, 367.957f)
            curveTo(240.416f, 367.957f, 229.333f, 356.693f, 229.333f, 340.992f)
            curveTo(229.333f, 325.973f, 240.762f, 314.709f, 256.0f, 314.709f)
            close()
            moveTo(277.333f, 128.0f)
            lineTo(277.333f, 277.333f)
            lineTo(234.667f, 277.333f)
            lineTo(234.667f, 128.0f)
            lineTo(277.333f, 128.0f)
            close()
        }
    }
        .build()

@Composable
fun FadeAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn(tween(600)),
    exit: ExitTransition = fadeOut(tween(600)),
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = enter,
        exit = exit,
    ) {
        content()
    }
}