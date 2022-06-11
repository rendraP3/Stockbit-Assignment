package com.stockbit.assignment.presentation.viewmodel

import com.stockbit.assignment.presentation.fragment.AuthFragmentDirections
import com.stockbit.common.base.BaseViewModel

class AuthViewModel : BaseViewModel() {

    fun navigateToMarket() {
        navigate(AuthFragmentDirections.toFragmentMarket())
    }

}