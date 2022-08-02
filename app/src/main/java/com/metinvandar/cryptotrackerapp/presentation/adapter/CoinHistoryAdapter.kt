package com.metinvandar.cryptotrackerapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.util.getFormattedDate
import com.metinvandar.cryptotrackerapp.databinding.CoinHistoryItemBinding
import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory

class CoinHistoryAdapter :
    ListAdapter<CoinHistory, CoinHistoryAdapter.CoinHistoryViewHolder>(HistoryItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHistoryViewHolder {
        val binding =
            CoinHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinHistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class HistoryItemDiffCallback : DiffUtil.ItemCallback<CoinHistory>() {
        override fun areItemsTheSame(oldItem: CoinHistory, newItem: CoinHistory): Boolean {
            return oldItem.coinId == newItem.coinId
        }

        override fun areContentsTheSame(
            oldItem: CoinHistory,
            newItem: CoinHistory
        ): Boolean {
            return oldItem.recordTime == newItem.recordTime
        }

    }

    inner class CoinHistoryViewHolder(private val itemViewBinding: CoinHistoryItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
            fun bind(historyItem: CoinHistory) {
                itemViewBinding.run {
                    coinPrice.text =  itemView.context.getString(
                        R.string.price_with_currency,
                        historyItem.price.toString()
                    )
                    recordTime.text = historyItem.recordTime.getFormattedDate()
                }
            }
        }
}
