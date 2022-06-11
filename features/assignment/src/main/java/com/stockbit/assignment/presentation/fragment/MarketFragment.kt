package com.stockbit.assignment.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stockbit.assignment.databinding.FragmentMarketBinding
import com.stockbit.assignment.presentation.viewmodel.MarketViewModel
import com.stockbit.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MarketFragment : BaseFragment<FragmentMarketBinding>() {

    private val viewModel by viewModel<MarketViewModel>()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMarketBinding = FragmentMarketBinding.inflate(inflater, container, false)

    override fun initView() {

    }

}