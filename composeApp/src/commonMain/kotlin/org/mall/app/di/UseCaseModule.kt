package org.mall.app.di


import org.mall.app.domain.usecases.ManageFetchData
import kotlinx.serialization.InternalSerializationApi
import org.koin.dsl.module

@OptIn(InternalSerializationApi::class)
val UseCaseModule = module {
    factory { ManageFetchData(get()) }
}