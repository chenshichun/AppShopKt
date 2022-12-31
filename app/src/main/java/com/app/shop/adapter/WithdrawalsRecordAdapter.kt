package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.CashDetailBean
import com.app.shop.databinding.ItemWithdrawalsRecordBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
class WithdrawalsRecordAdapter(private val context: Context, val mData: List<CashDetailBean>?) :
    RecyclerView.Adapter<WithdrawalsRecordAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWithdrawalsRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var status = "";
        when (mData!![position].status) {
            0 -> status = "提现中"
            1 -> status = "已提现"
            2 -> status = "已驳回"
        }

        holder.tvStatus.text = status
        holder.tvAlipayAccount.text =
            String.format(context.getString(R.string.alipay_account), mData[position].account)
        holder.tvAlipayName.text =
            String.format(context.getString(R.string.alipay_name), mData[position].name)
        holder.tvTime.text =
            String.format(context.getString(R.string.start_time), mData[position].created_at)
        holder.tvPrice.text =
            String.format(context.getString(R.string.price), mData[position].cash_out)

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

    inner class ViewHolder(binding: ItemWithdrawalsRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvStatus: TextView = binding.tvStatus
        val tvAlipayAccount: TextView = binding.tvAlipayAccount
        val tvAlipayName: TextView = binding.tvAlipayName
        val tvTime: TextView = binding.tvTime
        val tvPrice: TextView = binding.tvPrice
    }
}