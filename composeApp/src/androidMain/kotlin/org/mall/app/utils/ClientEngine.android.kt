package org.mall.app.utils

import io.ktor.client.engine.HttpClientEngine

actual fun getHttpClientEngine(): HttpClientEngine {
    return io.ktor.client.engine.okhttp.OkHttp.create()
}