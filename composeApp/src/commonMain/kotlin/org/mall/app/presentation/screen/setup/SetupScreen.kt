package org.mall.app.presentation.screen.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import org.mall.app.presentation.base.EventHandler
import org.mall.app.presentation.composables.MallEditTextWithTitle
import org.mall.app.presentation.composables.MallScaffold


@OptIn(KoinExperimentalAPI::class)
@Composable
fun SetupScreen(viewModel: SetupViewModel = koinViewModel { parametersOf() }) {
    val state by viewModel.state.collectAsState()
    EventHandler(viewModel.effect) { effect, _ ->
        when (effect) {

            else -> {}
        }
    }
    SetupContent(state, viewModel)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetupContent(
    state: SetupUiState,
    listener: SetupInteractionListener
) {
    MallScaffold(
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        showError = state.errorDialogueIsVisible,
        onDismissError = listener::onDismissError,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    Modifier.fillMaxSize().padding(top = 8.dp)
                        .border(0.25.dp, Color(0xff1A95D6), RoundedCornerShape(16.dp))
                        .padding(top = 8.dp).padding(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MallEditTextWithTitle(
                            value = state.url,
                            onValueChange = listener::onChangeUrl,
                            title = "URL",
                            modifier = Modifier.weight(1f)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        if (state.selectedOperation == Operation.GET) Color.Blue else Color.Gray,
                                        RoundedCornerShape(4.dp)
                                    ).clip(RoundedCornerShape(4.dp))
                                    .clickable { listener.onChangeOperation(Operation.GET) }
                                    .shadow(1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "GET",
                                    style = TextStyle(
                                        color = White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    ),
                                )
                            }
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        if (state.selectedOperation == Operation.POST) Color.Red else Color.Gray,
                                        RoundedCornerShape(4.dp)
                                    ).clip(RoundedCornerShape(4.dp))
                                    .clickable { listener.onChangeOperation(Operation.POST) }
                                    .shadow(1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "POST",
                                    style = TextStyle(
                                        color = White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    ),
                                )
                            }
                        }
                    }

                    MallEditTextWithTitle(
                        value = state.keyPath,
                        onValueChange = listener::onChangeKeyPath,
                        title = "Key Path",
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MallEditTextWithTitle(
                            value = state.username,
                            onValueChange = listener::onChangeUsername,
                            title = "Username",
                            modifier = Modifier.weight(1f)
                        )
                        MallEditTextWithTitle(
                            value = state.password,
                            onValueChange = listener::onChangePassword,
                            title = "Password",
                            modifier = Modifier.weight(1f)
                        )
                        MallEditTextWithTitle(
                            value = state.bearer,
                            onValueChange = listener::onChangeBearer,
                            title = "Bearer Token",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        FlowRow(
                            modifier = Modifier.padding(top = 8.dp)
                                .border(0.25.dp, Color(0xff1A95D6), RoundedCornerShape(16.dp))
                                .padding(top = 8.dp).padding(8.dp),
                            maxItemsInEachRow = 7,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            MallEditTextWithTitle(
                                value = state.keyMapping.get("number") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("number", it)
                                },
                                title = "invoice Number",
                                modifier = Modifier.weight(1f)
                            )

                            MallEditTextWithTitle(
                                value = state.keyMapping.get("date") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("date", it)
                                },
                                title = "invoice Date",
                                modifier = Modifier.weight(1f)
                            )
                            MallEditTextWithTitle(
                                value = state.keyMapping.get("subtotal") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("subtotal", it)
                                },
                                title = "subtotal",
                                modifier = Modifier.weight(1f)
                            )
                            MallEditTextWithTitle(
                                value = state.keyMapping.get("tax") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("tax", it)
                                },
                                title = "tax",
                                modifier = Modifier.weight(1f)
                            )

                            MallEditTextWithTitle(
                                value = state.keyMapping.get("total") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("total", it)
                                },
                                title = "total",
                                modifier = Modifier.weight(1f)
                            )
                            MallEditTextWithTitle(
                                value = state.keyMapping.get("service") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("service", it)
                                },
                                title = "service",
                                modifier = Modifier.weight(1f)
                            )
                            MallEditTextWithTitle(
                                value = state.keyMapping.get("discount") ?: "",
                                onValueChange = {
                                    listener.onChangeKeyMapping("discount", it)
                                },
                                title = "discount",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Text(
                            "Key Mapping",
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            modifier = Modifier.padding(start = 16.dp)
                                .background(Color.White).padding(horizontal = 4.dp)
                        )
                    }
                    KeyValueEditor(state, listener)
                }
                Text(
                    "Get Data From",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                        .background(Color.White).padding(horizontal = 4.dp)
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    Modifier.fillMaxSize().padding(top = 8.dp)
                        .border(0.25.dp, Color(0xff1A95D6), RoundedCornerShape(16.dp))
                        .padding(top = 8.dp).padding(8.dp),
                ) {

                }
                Text(
                    "Send Data To",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                        .background(Color.White).padding(horizontal = 4.dp)
                )
            }
        }

    }
}

@Composable
fun KeyValueEditor(state: SetupUiState, listener: SetupInteractionListener) {

    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp)
                .border(0.25.dp, Color(0xff1A95D6), RoundedCornerShape(16.dp))
                .padding(top = 8.dp).padding(8.dp)
        ) {
            LazyColumn(modifier = Modifier) {
                items(state.headers.size) { index ->
                    val pair = state.headers[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        MallEditTextWithTitle(
                            value = pair.first,
                            onValueChange = { newKey ->
                                listener.onChangeHeader(index, newKey to pair.second)
                            },
                            title = "Key",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MallEditTextWithTitle(
                            value = pair.second,
                            onValueChange = { newValue ->
                                listener.onChangeHeader(index, pair.first to newValue)
                            },
                            title = "Value",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.size(24.dp).clip(CircleShape)
                                .clickable { listener.onRemoveHeader(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier.size(24.dp).clip(CircleShape)
                        .clickable { listener.addNewHeader() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        }
        Text(
            "Headers",
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.padding(start = 16.dp)
                .background(Color.White).padding(horizontal = 4.dp)
        )
    }
}