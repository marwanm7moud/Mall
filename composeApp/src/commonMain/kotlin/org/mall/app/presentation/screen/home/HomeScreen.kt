package org.mall.app.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import org.mall.app.VerticalScrollableBar
import org.mall.app.data.model.Order
import org.mall.app.presentation.base.EventHandler
import org.mall.app.presentation.composables.MallEditTextWithTitle
import org.mall.app.presentation.composables.MallScaffold
import org.mall.app.presentation.navigation.LocalWindowManager
import org.mall.app.presentation.navigation.WindowRoute

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel { parametersOf() }) {
    val state by viewModel.state.collectAsState()
    EventHandler(viewModel.effect) { effect, _ ->
        when (effect) {

            else -> {}
        }
    }
    HomeContent(state, viewModel)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeContent(
    state: HomeUiState,
    listener: HomeInteractionListener
) {
    val lazyListState = rememberLazyListState()
    val windowManager = LocalWindowManager.current

    MallScaffold(
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        showError = state.errorDialogueIsVisible,
        onDismissError = listener::onDismissError,
    ) {
        Column() {
            Column(Modifier.fillMaxWidth()) {
                Button(onClick = {windowManager.openWindow(WindowRoute.SetupScreenRoute)}) {
                    Text("open setup")
                }
                Button(onClick = listener::onClickGetData) {
                    Text("Fetch Data")
                }
            }
            Column(Modifier.weight(1f).fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight().shadow(
                            2.dp,
                            RoundedCornerShape(16.dp),
                            spotColor = Color(0xff007AFF),
                            ambientColor = Color(0xff007AFF),
                        )
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .weight(1f),
                ) {
                    MallOrderLazy(
                        state = lazyListState,
                        selectedItem = state.selectedOrder,
                        itemsSetupUiState = state.orders,
                        onChangeSelectedItem = listener::onSelectOrder
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MallOrderLazy(
    state: LazyListState,
    itemsSetupUiState: List<Order>,
    selectedItem: Order?,
    onChangeSelectedItem: (Order) -> Unit
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxHeight()
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xffE6F2FF))
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Invoice Number",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Invoice Date",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Subtotal",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Tax",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Total",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Service",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "Discount",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp
                    )
                )
            }
        }

        items(itemsSetupUiState) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = if (selectedItem != it) Color.White else Color(0xff1A95D6))
                    .drawBehind {
                        val strokeWidth = 0.25f * density
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            Color(0xffD3D3D3),
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }
                    .clickable(onClick = { onChangeSelectedItem(it) })
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.number.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.date.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.subtotal.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.tax.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.total.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.service.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = it.discount.toString(),
                    modifier = Modifier.weight(1f), style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = if (selectedItem == it) Color.White else Color(0xff262626),
                        fontWeight = FontWeight.Light,
                        letterSpacing = 0.sp
                    )
                )
            }
        }
    }
    VerticalScrollableBar(state, modifier = Modifier.padding(top = 48.dp))
}