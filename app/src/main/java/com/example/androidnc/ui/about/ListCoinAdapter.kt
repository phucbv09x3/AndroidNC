package com.example.androidnc.ui.about

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnc.data.local.room.CoinModelRoom
import com.example.androidnc.databinding.ItemCoinBinding
import com.example.androidnc.ui.list.ListCoinViewModel
import kotlinx.android.synthetic.main.item_coin.view.*
class ListCoinAdapter(var listCoin: MutableList<CoinModelRoom>) :
    RecyclerView.Adapter<ListCoinAdapter.ListCoinViewHolder>() {
    fun setList(mutableList: MutableList<CoinModelRoom>) {
        val callback = NotifyDiffCallBack(listCoin, mutableList)
        val result = DiffUtil.calculateDiff(callback)
        listCoin = mutableList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListCoinViewHolder {
        val ifl = LayoutInflater.from(parent.context)
        val dataBinding = ItemCoinBinding.inflate(ifl, parent, false)
        return ListCoinViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ListCoinViewHolder, position: Int) {
        // Log.d("bind","${listCoin[position].name}")
        holder.setUp(listCoin[position])


    }

    override fun getItemCount() = listCoin.size
    inner class ListCoinViewHolder(
        private var dataBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        fun setUp(itemData: CoinModelRoom) {
            dataBinding.executePendingBindings()
            itemView.tv_name.text = itemData.name
            itemView.tv_symbol.text = itemData.symbol
            itemView.tv_slug.text = itemData.slug
            itemView.tv_price_old.text = itemData.price.toString()
            if (itemData.isCheckPrice == ListCoinViewModel.VALUE_UP) {
                itemView.tv_price_old.setTextColor(Color.BLUE)
            } else {
                itemView.tv_price_old.setTextColor(Color.YELLOW)
            }
            itemView.tv_up_down.text = itemData.isCheckPrice.toString()
        }
    }

    class NotifyDiffCallBack(
        var oldList: MutableList<CoinModelRoom>,
        var newList: MutableList<CoinModelRoom>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList == newList
    }
}