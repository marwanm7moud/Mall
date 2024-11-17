package org.mall.app.domain.dataSources

import kotlinx.serialization.json.JsonObject
import org.mall.app.data.model.Order

interface ApiDataSource {
    suspend fun fetchOrdersWithPost(
        url: String,
        username: String? = null,
        password: String? = null,
        bearer: String? = null,
        headers:List<Pair<String , String>> = emptyList()
    ): JsonObject

    suspend fun fetchOrdersWithGet(
        url: String,
        username: String? = null,
        password: String? = null,
        bearer: String? = null,
        headers:List<Pair<String , String>> = emptyList()
    ): JsonObject
}