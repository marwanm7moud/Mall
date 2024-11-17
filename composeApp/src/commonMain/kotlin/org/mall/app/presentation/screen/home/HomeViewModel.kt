package org.mall.app.presentation.screen.home

import io.ktor.http.ContentDisposition.Companion.File
import org.koin.core.component.KoinComponent
import org.mall.app.configFilePath
import org.mall.app.data.model.Order
import org.mall.app.domain.usecases.ManageFetchData
import org.mall.app.loadConfiguration
import org.mall.app.presentation.base.BaseViewModel
import org.mall.app.presentation.base.ErrorState
import org.mall.app.saveConfiguration

class HomeViewModel(
    private val useCase: ManageFetchData
) : BaseViewModel<HomeUiState, HomeUiEvent>(HomeUiState()), KoinComponent, HomeInteractionListener {


    init {
        loadConfig()
    }
    override fun onChangeKeyMapping(key: String, value: String) {
        updateState { it.copy(keyMapping = it.keyMapping - key + (key to value)) }
        saveConfig()
    }

    override fun onClickGetData() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                useCase.fetchOrdersWithPost(
                    url = state.value.url,//"https://Magrabi-ic-magrabic.integration.ocp.oraclecloud.com:443/ic/api/integration/v1/flows/rest/DANDY_MALL_SALES/1.0/sales",
                    username = state.value.username,//"DANDY_BASICAUTH",
                    password = state.value.password,//"a72f3030-8a9c-4adb-bf96-3d7a06b00fd9",
                    keyPath = state.value.keyPath,//result
                    keyMapping = state.value.keyMapping,
                    headers = state.value.headers
                )
            },
            onSuccess = { orders ->
                updateState { it.copy(isLoading = false) }
                updateState { it.copy(orders = orders) }
                println(orders)
            },
            onError = ::onError
        )
        saveConfig()
    }

    private fun onError(error: ErrorState) {
        updateState {
            it.copy(
                isLoading = false,
                errorDialogueIsVisible = true,
                errorMessage = when (error) {
                    is ErrorState.NetworkError -> error.message.toString()
                    is ErrorState.NotFound -> error.message.toString()
                    is ErrorState.ServerError -> error.message.toString()
                    is ErrorState.PermissionDenied -> error.message.toString()
                    is ErrorState.UnknownError -> error.message.toString()
                    is ErrorState.EmptyData -> error.message.toString()
                    is ErrorState.ValidationError -> error.message.toString()
                    is ErrorState.ValidationNetworkError -> error.message.toString()
                    is ErrorState.UnAuthorized -> error.message.toString()
                }
            )
        }
        println(error)
    }

    override fun onDismissError() {
        updateState {
            it.copy(
                errorMessage = "",
                errorDialogueIsVisible = false,
            )
        }
    }

    private fun loadConfig() {
        val loadedConfig = loadConfiguration(configFilePath)
        updateState {
            it.copy(
                url = loadedConfig?.url ?:"",
                keyPath = loadedConfig?.keyPath ?: "",
                keyMapping = loadedConfig?.keyMapping ?: mapOf(
                    "number" to "invoice_no",
                    "date" to "invoice_date",
                    "subtotal" to "subtotal",
                    "tax" to "tax",
                    "total" to "total",
                    "service" to "service",
                    "discount" to "discount"
                ),
                headers = loadedConfig?.headers ?: listOf(),
                username = loadedConfig?.username ?: "",
                password = loadedConfig?.password ?: "",
                bearer = loadedConfig?.bearer ?: ""
            )
        }
    }

    private fun saveConfig() {
        val newConfig = Configuration(
            url = state.value.url,
            keyPath = state.value.keyPath,
            keyMapping = state.value.keyMapping,
            headers = state.value.headers,
            username = state.value.username,
            password = state.value.password,
            bearer = state.value.bearer
        )
        saveConfiguration(newConfig, configFilePath)
    }


    override fun onChangeUrl(value: String) {
        updateState { it.copy(url = value) }
        saveConfig()
    }

    override fun onChangeKeyPath(value: String) {
        updateState { it.copy(keyPath = value) }
        saveConfig()
    }

    override fun onChangeUsername(value: String) {
        updateState { it.copy(username = value) }
        saveConfig()
    }

    override fun onChangePassword(value: String) {
        updateState { it.copy(password = value) }
        saveConfig()
    }

    override fun onChangeBearer(value: String) {
        updateState { it.copy(bearer = value) }
        saveConfig()
    }

    override fun onSelectOrder(order: Order) {
        updateState { it.copy(selectedOrder = order) }
        saveConfig()
    }

}


