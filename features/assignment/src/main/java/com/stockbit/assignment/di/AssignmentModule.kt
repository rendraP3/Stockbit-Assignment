package com.stockbit.assignment.di

import com.stockbit.assignment.presentation.viewmodel.AuthViewModel
import com.stockbit.assignment.presentation.viewmodel.MarketViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val assignmentModule = module {
    viewModel { AuthViewModel() }
    viewModel { MarketViewModel(get(), get(), get()) }
}