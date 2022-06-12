package com.stockbit.assignment.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stockbit.assignment.R
import com.stockbit.assignment.databinding.FragmentMarketBinding
import com.stockbit.assignment.presentation.adapter.MarketAdapter
import com.stockbit.assignment.presentation.viewmodel.MarketViewModel
import com.stockbit.assignment.utils.Constants.BASE_URL
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.extension.hide
import com.stockbit.common.extension.orFalse
import com.stockbit.common.extension.show
import com.stockbit.common.utils.ErrorState
import com.stockbit.model.Result
import com.stockbit.model.dto.response.TopListVolumeFullDataResponse
import org.koin.android.viewmodel.ext.android.viewModel

class MarketFragment : BaseFragment<FragmentMarketBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModel<MarketViewModel>()

    private val marketAdapter by lazy {
        MarketAdapter {
            activity?.startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(BASE_URL + it.url)
                }
            )
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMarketBinding = FragmentMarketBinding.inflate(inflater, container, false)

    override fun initView() {
        observeNavigation(viewModel)
        initUI()
        viewModel.resetValue()
        viewModel.getTotalVolFull()
        setupObservers()
    }

    private fun initUI() = with(binding) {
        rvMarket.apply {
            adapter = marketAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
        swipeContainer.setOnRefreshListener(this@MarketFragment)
        errorView.retryListener = { onRefresh() }
        scrollContainer.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight) && viewModel.isPreviousDataLoaded)
                    viewModel.getTotalVolFull(isNextPage = true)
            }
        )
    }

    private fun setupObservers() {
        observeTopTotalList()
        observeTicker()
    }

    private fun observeTopTotalList() = with(viewModel) {
        topTotalVolFull.observe(viewLifecycleOwner) {
            isPreviousDataLoaded = it.status == Result.Status.SUCCESS
            when (it.status) {
                Result.Status.SUCCESS -> onTopTotalSuccess(it.data)
                Result.Status.LOADING -> onTopTotalLoading()
                Result.Status.ERROR -> onTopTotalError(it)
            }
        }
    }

    private fun observeTicker() = with(viewModel) {
//        observeSocketData.observe(viewLifecycleOwner) {
//            if (it.openDay > 0) marketAdapter.openDay[it.fromSymbol] = it.openDay
//            marketAdapter.updatePrice(it)
//        }
    }

    private fun onTopTotalSuccess(data: TopListVolumeFullDataResponse?) = with(binding) {
//        viewModel.connectToWebSocket(data?.data)
        if (viewModel.isLoadNextPage) {
            tvLoadMore.hide()
            marketAdapter.submitList(marketAdapter.currentList.plus(data?.data.orEmpty()))
        } else {
            hideShimmer()
            if (data?.data?.isNotEmpty().orFalse()) {
                rvMarket.show()
                marketAdapter.submitList(data?.data)
                errorView.hide()
            } else {
                rvMarket.hide()
                errorView.apply {
                    show()
                    setError(ErrorState.EMPTY, getString(R.string.error_empty))
                }
            }
        }
    }

    private fun onTopTotalLoading() = with(binding) {
        if (marketAdapter.currentList.isEmpty() && !viewModel.isLoadNextPage) {
            showShimmer()
            rvMarket.hide()
            errorView.hide()
        } else tvLoadMore.show()
    }

    private fun onTopTotalError(result: Result<TopListVolumeFullDataResponse>) = with(binding) {
        hideShimmer()
        tvLoadMore.hide()
        errorView.apply {
            show()
            setError(ErrorState.ERROR, result.message.orEmpty())
        }
    }

    private fun showShimmer() = with(binding) {
        rvShimmer.show()
        rvShimmer.showShimmerAdapter()
    }

    private fun hideShimmer() = with(binding) {
        rvShimmer.hide()
        rvShimmer.hideShimmerAdapter()
    }

    override fun onRefresh() = with(binding) {
        swipeContainer.isRefreshing = false
        marketAdapter.submitList(null)
        showShimmer()
        rvMarket.hide()
        viewModel.resetValue()
        viewModel.getTotalVolFull()
    }

}