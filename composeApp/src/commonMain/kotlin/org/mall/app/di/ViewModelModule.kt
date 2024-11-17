package org.mall.app.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.mall.app.presentation.screen.home.HomeViewModel


val ViewModelModule = module {
    //viewModelOf(::GuestInformationViewModel)
    viewModelOf(::HomeViewModel)
//    viewModel {
//        GuestInformationViewModel(
//            guestUseCase = get<ManageGuestUseCase>(),
//        )
//    }
}