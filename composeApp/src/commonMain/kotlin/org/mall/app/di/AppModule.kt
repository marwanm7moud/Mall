package org.mall.app.di

import org.koin.dsl.module

val AppModule = module(createdAtStart = true) {
    includes(ViewModelModule , NetworkModule , DataSourceModule , UseCaseModule)
}