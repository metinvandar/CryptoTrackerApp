package com.metinvandar.cryptotrackerapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.util.visible
import com.metinvandar.cryptotrackerapp.databinding.CoinListItemBinding
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import kotlin.math.abs

class CoinListAdapter :
    ListAdapter<CoinDomainModel, CoinListAdapter.CoinViewHolder>(CoinItemDiffCallback()) {

    var onItemClick: ((coin: CoinDomainModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding =
            CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind()
    }

    private class CoinItemDiffCallback : DiffUtil.ItemCallback<CoinDomainModel>() {
        override fun areItemsTheSame(oldItem: CoinDomainModel, newItem: CoinDomainModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CoinDomainModel,
            newItem: CoinDomainModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.symbol == newItem.symbol &&
                    oldItem.currentPrice == newItem.currentPrice
        }

    }

    inner class CoinViewHolder(private val itemViewBinding: CoinListItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind() {
            val coinItem = currentList[adapterPosition]
            val context = itemView.context
            itemViewBinding.run {
                itemViewBinding.coinPrice.text =
                    context.getString(
                        R.string.price_with_currency,
                        coinItem.currentPrice.toString()
                    )
                if (coinItem.priceChangePercentage > 0) {
                    itemViewBinding.coinPriceChange.text = context.getString(
                        R.string.value_with_percentage,
                        coinItem.priceChangePercentage.toString()
                    )
                    priceChangeArrow.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.vc_arrow_upward
                        )
                    )
                } else if (coinItem.priceChangePercentage < 0) {
                    val priceChange = -abs(coinItem.priceChangePercentage)
                    itemViewBinding.coinPriceChange.text =
                        context.getString(R.string.value_with_percentage, priceChange.toString())
                    priceChangeArrow.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.vc_arrow_downward
                        )
                    )
                } else {
                    priceChangeArrow.visible = false
                }
                coin = coinItem
                root.setOnClickListener { onItemClick?.invoke(coinItem) }
                executePendingBindings()
            }
        }
    }
}
