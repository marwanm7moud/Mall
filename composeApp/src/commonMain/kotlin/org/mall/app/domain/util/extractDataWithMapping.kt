package org.mall.app.domain.util

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.mall.app.data.model.Order

fun extractDataWithMapping(
    json: JsonObject,
    keyPath: String,
    keyMapping: Map<String, String>
): List<Order> {
    val keys = keyPath.split(".")
    var currentElement: JsonElement = json

    for (key in keys) {
        currentElement = currentElement.jsonObject[key]
            ?: throw IllegalArgumentException("Key $key not found in the JSON.")
    }

    // Check if the final element is a JSON array
    if (currentElement is JsonArray) {
        return currentElement.map { jsonElement ->
            val jsonObject = jsonElement.jsonObject
            Order(
                number = jsonObject[keyMapping["number"]]?.jsonPrimitive?.content,
                date = jsonObject[keyMapping["date"]]?.jsonPrimitive?.content,
                subtotal = jsonObject[keyMapping["subtotal"]]?.jsonPrimitive?.double.toString(),
                tax = jsonObject[keyMapping["tax"]]?.jsonPrimitive?.double.toString(),
                total = jsonObject[keyMapping["total"]]?.jsonPrimitive?.double,
                service = jsonObject[keyMapping["service"]]?.jsonPrimitive?.content,
                discount = jsonObject[keyMapping["discount"]]?.jsonPrimitive?.content
            )
        }
    } else {
        throw IllegalArgumentException("The final key does not point to a JSON array.")
    }
}