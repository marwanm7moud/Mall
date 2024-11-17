package org.mall.app.data.utils

import com.abapps.hotel.domain.util.NetworkException
import com.abapps.hotel.domain.util.NotFoundException
import com.abapps.hotel.domain.util.PermissionDeniedException
import com.abapps.hotel.domain.util.ServerErrorException
import com.abapps.hotel.domain.util.UnAuthException
import com.abapps.hotel.domain.util.UnknownErrorException
import com.abapps.hotel.domain.util.ValidationNetworkException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.mall.app.data.model.ServerResponse

open class BaseGateway(val client: HttpClient) {
    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            when (e.response.body<ServerResponse<T>>().status.code) {
                400 -> throw ValidationNetworkException(errorMessages)
                401 -> throw UnAuthException(errorMessages)
                404 -> throw NotFoundException(errorMessages)
                405 -> throw PermissionDeniedException(errorMessages)
                else -> throw UnknownErrorException(errorMessages)
            }
        } catch (e: RedirectResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw UnknownErrorException(errorMessages)
        } catch (e: ServerResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw ServerErrorException(errorMessages)
        } catch (e: IOException) {
            val errorMessages = e.message.toString()
            throw NetworkException(errorMessages)
        } catch (e: Exception) {
            val errorMessages = e.message.toString()
            throw UnknownErrorException(errorMessages)
        }
    }
    suspend inline fun <reified T> tryToExecuteReturnResponseString(method: HttpClient.() -> HttpResponse): JsonObject {
        try {
            return Json.parseToJsonElement(client.method().bodyAsText()).jsonObject
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            when (e.response.body<ServerResponse<T>>().status.code) {
                400 -> throw ValidationNetworkException(errorMessages)
                401 -> throw UnAuthException(errorMessages)
                404 -> throw NotFoundException(errorMessages)
                405 -> throw PermissionDeniedException(errorMessages)
                else -> throw UnknownErrorException(errorMessages)
            }
        } catch (e: RedirectResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw UnknownErrorException(errorMessages)
        } catch (e: ServerResponseException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.messageError
            throw ServerErrorException(errorMessages)
        } catch (e: IOException) {
            val errorMessages = e.message.toString()
            throw NetworkException(errorMessages)
        } catch (e: Exception) {
            val errorMessages = e.message.toString()
            throw UnknownErrorException(errorMessages)
        }
    }
}