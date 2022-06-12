package com.stockbit.assignment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sandbox.domain.domain.ConnectToWebSocketUseCase
import com.sandbox.domain.domain.GetTopTotalVolumeFullUseCase
import com.sandbox.domain.domain.ReceiveResponseUseCase
import com.stockbit.common.base.BaseViewModel
import com.stockbit.model.Result
import com.stockbit.model.dto.request.SubscriptionRequest
import com.stockbit.model.dto.response.TopListData
import com.stockbit.model.dto.response.TopListVolumeFullDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn

class MarketViewModel(
    private val getTopTotalVolumeFullUseCase: GetTopTotalVolumeFullUseCase,
    private val connectToWebSocketUseCase: ConnectToWebSocketUseCase,
    private val receiveResponseUseCase: ReceiveResponseUseCase
) : BaseViewModel() {

    private val _topTotalVolFull = MutableLiveData<Result<TopListVolumeFullDataResponse>>()
    val topTotalVolFull: LiveData<Result<TopListVolumeFullDataResponse>> = _topTotalVolFull

    var currency = "IDR"
    var currentPage = 1
    var isPreviousDataLoaded = false
    var isLoadNextPage = false

    val observeSocketData by lazy {
        receiveResponseUseCase().flowOn(Dispatchers.IO).filter { it.type == 5 }.asLiveData()
    }

    fun resetValue() {
        currentPage = 1
    }

    fun getTotalVolFull(isNextPage: Boolean = false) {
        if (isNextPage) currentPage++
        isLoadNextPage = isNextPage

        launchWithViewModel {
            getTopTotalVolumeFullUseCase(
                page = currentPage,
                currency = currency
            ).collect { _topTotalVolFull.postValue(it) }
        }
    }

    fun connectToWebSocket(data: List<TopListData>?) {
        val subs = mutableListOf<String>()
        data?.let {
            it.forEach { topList ->
                subs.add("5~CCCAGG~${topList.coinInfo.name}~$currency")
            }
        }
        connectToWebSocketUseCase(
            SubscriptionRequest(
                action = "SubAdd",
                subs = subs
            )
        )
    }

}