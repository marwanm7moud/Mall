package org.mall.app.di

import org.mall.app.data.ApiDataSourceImpl
import org.mall.app.domain.dataSources.ApiDataSource
import org.koin.dsl.bind
import org.koin.dsl.module

val DataSourceModule = module {
    single { ApiDataSourceImpl(get()) } bind ApiDataSource::class

}