package org.mall.app.data

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
import io.ktor.client.request.basicAuth
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.append
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.mall.app.SalesRequest
import org.mall.app.data.model.Order
import org.mall.app.data.model.ServerResponse
import org.mall.app.data.utils.BaseGateway
import org.mall.app.domain.dataSources.ApiDataSource

@OptIn(InternalAPI::class)
class ApiDataSourceImpl(client: HttpClient) : ApiDataSource, BaseGateway(client) {

    override suspend fun fetchOrdersWithPost(
        url: String,
        username: String?,
        password: String?,
        bearer: String?,
        headers: List<Pair<String, String>>
    ): JsonObject {
        return tryToExecuteReturnResponseString<ServerResponse<String>> {
            client.post(block = {
                if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
                    basicAuth(username, password)
                }
                if (!bearer.isNullOrBlank()) {
                    bearerAuth(bearer)
                }
                if (headers.isNotEmpty()) {
                    headers {
                        headers.forEach {
                            append(name = it.first, value = it.second)
                        }
                    }
                }
                url(url)
                contentType(io.ktor.http.ContentType.Application.Json)
                body = Json.encodeToString(
                    SalesRequest.serializer(), SalesRequest(
                        startDate = "2024-02-01",
                        endDate = "2024-03-09",
                        page = "1",
                        storeID = "9509"
                    )
                )
            })
        }
    }

    override suspend fun fetchOrdersWithGet(
        url: String,
        username: String?,
        password: String?,
        bearer: String?,
        headers: List<Pair<String, String>>
    ): JsonObject {
        return tryToExecuteReturnResponseString<ServerResponse<JsonObject>> {
            client.post(block = {
                if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
                    basicAuth(username, password)
                }
                if (!bearer.isNullOrBlank()) {
                    bearerAuth(bearer)
                }
                if (headers.isNotEmpty()) {
                    headers {
                        headers.forEach {
                            append(name = it.first, value = it.second)
                        }
                    }
                }
                url(url)
                contentType(io.ktor.http.ContentType.Application.Json)
                body = Json.encodeToString(
                    SalesRequest.serializer(), SalesRequest(
                        startDate = "2024-02-01",
                        endDate = "2024-03-09",
//                        page = "1",
//                        storeID = "9509"
                    )
                )
            })
        }
    }
}

