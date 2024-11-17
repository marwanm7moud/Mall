package org.mall.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform