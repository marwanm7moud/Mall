package org.mall.app.presentation.screen.home

import io.ktor.http.ContentDisposition.Companion.File
import org.koin.core.component.KoinComponent
import org.mall.app.configFilePath
import org.mall.app.data.model.Order
import org.mall.app.domain.usecases.ManageFetchData
import org.mall.app.loadConfiguration
import org.mall.app.presentation.base.BaseViewModel
import org.mall.app.presentation.base.ErrorState
import org.mall.app.presentation.screen.setup.Operation
import org.mall.app.saveConfiguration

class HomeViewModel(
    private val useCase: ManageFetchData
) : BaseViewModel<HomeUiState, HomeUiEvent>(HomeUiState()), KoinComponent, HomeInteractionListener {


    override fun onClickGetData() {
        loadConfig()
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                when(state.value.configuration.operation){
                    Operation.POST -> {
                        useCase.fetchOrdersWithPost(
                            url = state.value.configuration.url,
                            username = state.value.configuration.username,
                            password = state.value.configuration.password,
                            keyPath = state.value.configuration.keyPath,
                            keyMapping = state.value.configuration.keyMapping,
                            headers = state.value.configuration.headers
                        )
                    }
                    Operation.GET -> {
                        useCase.fetchOrdersWithGet(
                            url = state.value.configuration.url,
                            username = state.value.configuration.username,
                            password = state.value.configuration.password,
                            keyPath = state.value.configuration.keyPath,
                            keyMapping = state.value.configuration.keyMapping,
                            headers = state.value.configuration.headers
                        )
                    }
                }
            },
            onSuccess = { orders ->
                updateState { it.copy(isLoading = false) }
                updateState { it.copy(orders = orders) }
                println(orders)
            },
            onError = ::onError
        )
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
                configuration = loadedConfig ?: Configuration()
            )
        }
    }
    override fun onSelectOrder(order: Order) {
        updateState { it.copy(selectedOrder = order) }
    }
}


