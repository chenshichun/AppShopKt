package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.WithdrawPriceBean
import com.app.shop.databinding.ItemWithdrawPriceBinding
import kotlin.coroutines.coroutineContext

/**
 * @author chenshichun
 * 创建日期：2022/8/22
 * 描述：
 */
class WithdrawPriceAdapter(private val context: Context, val mData: List<WithdrawPriceBean>?) :
    RecyclerView.Adapter<WithdrawPriceAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWithdrawPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvPrice.text = mData!![position].price
        holder.tvPrice.setTextColor(
            ContextCompat.getColor(
                context,
                if (mData[position].isCheck) R.color.blue else R.color.color_666
            )
        )
        holder.tvPrice.background = ContextCompat.getDrawable(
            context,
            if (mData[position].isCheck) R.drawable.hollow_blue_5_bg else R.drawable.hollow_gray_5_bg
        )
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemWithdrawPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvPrice: TextView = binding.tvPrice
    }
}