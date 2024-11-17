package org.mall.app

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.mall.app.data.model.Order


@OptIn(InternalAPI::class)
suspend fun fetchSalesDataOrders(): JsonObject {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    val responseString =
        client.post(block = {
            basicAuth(username = "DANDY_BASICAUTH" , "a72f3030-8a9c-4adb-bf96-3d7a06b00fd9")
            url("https://Magrabi-ic-magrabic.integration.ocp.oraclecloud.com:443/ic/api/integration/v1/flows/rest/DANDY_MALL_SALES/1.0/sales")
            contentType(io.ktor.http.ContentType.Application.Json)
            body =  Json.encodeToString(SalesRequest.serializer(), SalesRequest(
                startDate = "2024-02-01",
                endDate = "2024-03-09",
                page = "1",
                storeID = "9509"
            ))
        })
    println("MARWAN" + responseString.bodyAsText())
    return Json.parseToJsonElement(responseString.bodyAsText()).jsonObject
}

@Serializable
data class SalesRequest(
    val startDate: String,
    val endDate: String,
    val page: String? = null,
    val storeID: String? = null
)