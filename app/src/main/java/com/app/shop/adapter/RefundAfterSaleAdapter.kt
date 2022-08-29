package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemRefundAfterSaleBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 *
 */
class RefundAfterSaleAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<RefundAfterSaleAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRefundAfterSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val refundGoodsAdapter = RefundGoodsAdapter(context, null)
        holder.mRecyclerView.layoutManager = LinearLayoutManager(context)
        holder.mRecyclerView.adapter = refundGoodsAdapter

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(binding: ItemRefundAfterSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mRecyclerView: RecyclerView = binding.mRecyclerView
    }
}