package org.mall.app.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.mall.app.presentation.screen.home.HomeViewModel
import org.mall.app.presentation.screen.setup.SetupViewModel


val ViewModelModule = module {
    //viewModelOf(::GuestInformationViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SetupViewModel)
//    viewModel {
//        GuestInformationViewModel(
//            guestUseCase = get<ManageGuestUseCase>(),
//        )
//    }
}