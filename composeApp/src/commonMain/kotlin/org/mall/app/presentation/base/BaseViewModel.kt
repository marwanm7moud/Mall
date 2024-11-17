package org.mall.app.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abapps.hotel.domain.util.UnAuthException
import com.abapps.hotel.domain.util.NetworkException
import com.abapps.hotel.domain.util.NotFoundException
import com.abapps.hotel.domain.util.EmptyDataException
import com.abapps.hotel.domain.util.PermissionDeniedException
import com.abapps.hotel.domain.util.ServerErrorException
import com.abapps.hotel.domain.util.UnknownErrorException
import com.abapps.hotel.domain.util.ValidationException
import com.abapps.hotel.domain.util.ValidationNetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().throttleFirst(1000).mapNotNull { it }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope,
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            val result = function()
            onSuccess(result)
        }
    }

    protected suspend fun <T> tryToExecuteWithoutScope(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
    ) {
        try {
            val result = function()
            onSuccess(result)
        } catch (exception: UnAuthException) {
            onError(ErrorState.UnAuthorized(exception.message))
        } catch (exception: PermissionDeniedException) {
            onError(ErrorState.PermissionDenied(exception.message))
        } catch (exception: ValidationNetworkException) {
            onError(ErrorState.ValidationNetworkError(exception.message))
        } catch (exception: ValidationException) {
            onError(ErrorState.ValidationError(exception.message))
        } catch (exception: NotFoundException) {
            onError(ErrorState.NotFound(exception.message))
        } catch (exception: EmptyDataException) {
            onError(ErrorState.EmptyData(exception.message))
        } catch (exception: NetworkException) {
            onError(ErrorState.NetworkError(exception.message))
        } catch (exception: UnknownErrorException) {
            onError(ErrorState.UnknownError(exception.message))
        } catch (exception: ServerErrorException) {
            onError(ErrorState.ServerError(exception.message))
        } catch (exception: Exception) {
            onError(ErrorState.UnknownError(exception.message))
        }
    }

    protected fun <T> tryToCollect(
        function: suspend () -> Flow<T>,
        onNewValue: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope,
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            function().distinctUntilChanged().collectLatest {
                onNewValue(it)
            }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun sendNewEffect(newEffect: E) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    private fun runWithErrorCheck(
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        function: suspend () -> Unit,
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (exception: UnAuthException) {
                onError(ErrorState.UnAuthorized(exception.message))
            } catch (exception: PermissionDeniedException) {
                onError(ErrorState.PermissionDenied(exception.message))
            } catch (exception: ValidationNetworkException) {
                onError(ErrorState.ValidationNetworkError(exception.message))
            } catch (exception: ValidationException) {
                onError(ErrorState.ValidationError(exception.message))
            } catch (exception: NotFoundException) {
                onError(ErrorState.NotFound(exception.message))
            } catch (exception: EmptyDataException) {
                onError(ErrorState.EmptyData(exception.message))
            } catch (exception: NetworkException) {
                onError(ErrorState.NetworkError(exception.message))
            } catch (exception: UnknownErrorException) {
                onError(ErrorState.UnknownError(exception.message))
            } catch (exception: ServerErrorException) {
                onError(ErrorState.ServerError(exception.message))
            } catch (exception: Exception) {
                onError(ErrorState.UnknownError(exception.message))
            }
        }
    }

    private fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
        require(periodMillis > 0)
        return flow {
            var lastTime = 0L
            collect { value ->
                val currentTime = Clock.System.now().toEpochMilliseconds()
                if (currentTime - lastTime >= periodMillis) {
                    lastTime = currentTime
                    emit(value)
                }
            }
        }
    }

    protected fun launchDelayed(duration: Long, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            delay(duration)
            block()
        }
    }
}