package com.stockbit.assignment.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stockbit.assignment.BuildConfig.BASE_IMAGE_URL
import com.stockbit.assignment.R
import com.stockbit.assignment.databinding.ItemMarketBinding
import com.stockbit.common.extension.hide
import com.stockbit.model.dto.response.CoinInfo
import com.stockbit.model.dto.response.TickerResponse
import com.stockbit.model.dto.response.TopListData
import java.math.BigDecimal

class MarketAdapter(
    private val onItemClicked: (CoinInfo) -> Unit
) :
    ListAdapter<TopListData, MarketAdapter.MarketViewHolder>(MarketDiffUtilCallback()) {

    val openDay = mutableMapOf<String, Double>()
    val lastPrice = mutableMapOf<String, Double>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder =
        MarketViewHolder(
            ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePrice(ticker: TickerResponse) {
        currentList.find { it.coinInfo.name == ticker.fromSymbol }?.run {
            if (ticker.price > 0) coinInfo.price = ticker.price
            if (ticker.volume > 0) coinInfo.marketCap = ticker.volume
            coinInfo.currencySymbol = ticker.toSymbol
        }
        notifyDataSetChanged()
    }

    private class MarketDiffUtilCallback : DiffUtil.ItemCallback<TopListData>() {
        override fun areItemsTheSame(oldItem: TopListData, newItem: TopListData) =
            oldItem.coinInfo.id == newItem.coinInfo.id

        override fun areContentsTheSame(oldItem: TopListData, newItem: TopListData) =
            oldItem == newItem

    }

    inner class MarketViewHolder(private val binding: ItemMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: TopListData) = with(binding) {
            tvName.text = data.coinInfo.fullName
            tvSymbol.text = data.coinInfo.name
            lcGraph.hide()
            Glide.with(this.root).load("${BASE_IMAGE_URL}${data.coinInfo.imageUrl}").into(ivImage)

            tvMarketCap.text = "MCap ${data.coinInfo.currencySymbol}${marketCapConverter(data.coinInfo.marketCap)}"

            setPrice(data.coinInfo)
            setPercentage(data.coinInfo)
        }

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        private fun setPercentage(data: CoinInfo) = with(binding) {
            val open = openDay[data.name]
            if (open != null && data.price > 0) {
                val percent = (data.price / open) * 100 - 100
                tvGainLoss.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    when (percent.toBigDecimal().compareTo(BigDecimal.ZERO)) {
                        -1 -> root.context.getDrawable(
                            R.drawable.ic_loss
                        )
                        1 -> root.context.getDrawable(
                            R.drawable.ic_gain
                        )
                        else -> null
                    }, null, null, null
                )
                tvGainLoss.text = String.format("%,.2f", percent) + "%"
            }
        }

        @SuppressLint("SetTextI18n")
        private fun setPrice(data: CoinInfo) = with(binding) {
            val lastPrice = lastPrice[data.name]
            tvPrice.text = "${data.currencySymbol}${data.price}"
        }

        private fun marketCapConverter(marketCap: Double) = when {
            marketCap > 1000000000.0 -> String.format("%,.2fB", marketCap / 1000000000.0)
            marketCap > 1000000.0 && marketCap < 1000000000.0 -> String.format(
                "%,.2fM",
                marketCap / 1000000.0
            )
            marketCap > 1000.0 && marketCap < 1000000.0 -> String.format(
                "%,.2fK",
                marketCap / 1000.0
            )
            else -> String.format("%,.2f", marketCap)
        }

    }

}