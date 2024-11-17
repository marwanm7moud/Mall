package org.mall.app.presentation.screen.home

import io.ktor.http.Headers
import kotlinx.serialization.Serializable
import org.mall.app.data.model.Order
import org.mall.app.presentation.screen.setup.Operation

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorDialogueIsVisible: Boolean = false,
    val errorMessage: String = "",
    val configuration: Configuration = Configuration(),
    val orders: List<Order> = listOf(),
    val selectedOrder: Order? = null,
)

@Serializable
data class Configuration(
    val url: String = "",
    val keyPath: String = "",
    val keyMapping: Map<String, String> = mapOf(
        "number" to "",
        "date" to "",
        "subtotal" to "",
        "tax" to "",
        "total" to "",
        "service" to "",
        "discount" to ""
    ),
    val password: String= "",
    val username: String= "",
    val bearer: String= "",
    val operation: Operation = Operation.GET,
    val headers: List<Pair<String, String>> = listOf()
)