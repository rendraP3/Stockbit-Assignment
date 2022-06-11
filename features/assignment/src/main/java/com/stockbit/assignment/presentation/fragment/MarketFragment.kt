package com.stockbit.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stockbit.assignment.databinding.FragmentMarketBinding
import com.stockbit.assignment.presentation.viewmodel.MarketViewModel
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel

class MarketFragment : BaseFragment() {

    override fun getViewModel() = MarketViewModel()

    private lateinit var binding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}