package org.mall.app.domain.usecases

import org.mall.app.data.model.Order
import org.mall.app.domain.dataSources.ApiDataSource
import org.mall.app.domain.util.extractDataWithMapping

class ManageFetchData(private val dataSource: ApiDataSource) {

    suspend fun fetchOrdersWithPost(
        url: String,
        username: String? = null,
        password: String? = null,
        bearer: String? = null,
        keyPath: String,
        keyMapping: Map<String, String>,
        headers: List<Pair<String, String>> = emptyList()
    ): List<Order> {
        return extractDataWithMapping(
            dataSource.fetchOrdersWithPost(
                url,
                username,
                password,
                bearer,
                headers
            ), keyPath, keyMapping
        )
    }

    suspend fun fetchOrdersWithGet(
        url: String,
        username: String? = null,
        password: String? = null,
        bearer: String? = null,
        keyPath: String,
        keyMapping: Map<String, String>,
        headers: List<Pair<String, String>> = emptyList()

    ): List<Order> {
        return extractDataWithMapping(
            dataSource.fetchOrdersWithGet(
                url,
                username,
                password,
                bearer,
                headers
            ), keyPath, keyMapping
        )
    }
}