package org.mall.app.presentation.screen.setup

import org.mall.app.data.model.Order

data class SetupUiState(
    val isLoading: Boolean = false,
    val errorDialogueIsVisible: Boolean = false,
    val errorMessage: String = "",
    val url: String = "https://Magrabi-ic-magrabic.integration.ocp.oraclecloud.com:443/ic/api/integration/v1/flows/rest/DANDY_MALL_SALES/1.0/sales",
    val keyPath: String = "result",
    val username: String = "DANDY_BASICAUTH",
    val password: String = "a72f3030-8a9c-4adb-bf96-3d7a06b00fd9",
    val bearer: String = "",
    val selectedOperation: Operation = Operation.GET,
    val headers: List<Pair<String, String>> = listOf(),
    val selectedOrder: Order? = null,
    val keyMapping: Map<String, String> = mapOf(
        "number" to "invoice_no",
        "date" to "invoice_date",
        "subtotal" to "subtotal",
        "tax" to "tax",
        "total" to "total",
        "service" to "service",
        "discount" to "discount"
    ),
)

enum class Operation {
    POST,GET
}