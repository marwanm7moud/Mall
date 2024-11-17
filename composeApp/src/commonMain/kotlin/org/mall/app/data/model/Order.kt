package org.mall.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val number: String? = null,
    val date: String? = null,
    val subtotal: String? = null,
    val tax: String? = null,
    val total: Double? = null,
    val service: String? = null,
    val discount: String? = null
)