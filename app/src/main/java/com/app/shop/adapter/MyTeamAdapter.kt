package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.ListBean
import com.app.shop.databinding.ItemTeamBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/22
 * 描述：
 */
class MyTeamAdapter(private val context: Context, val mData: List<ListBean>?) :
    RecyclerView.Adapter<MyTeamAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNickname.text =
            String.format(context.getString(R.string.nickname), mData!![position].nick)
        holder.tvAccount.text =
            String.format(context.getString(R.string.account), mData!![position].phone)
        holder.tvLevel.text =
            String.format(context.getString(R.string.level), mData!![position].level)
        holder.tvAllCount.text =
            String.format(context.getString(R.string.all_count), mData!![position].all_count)
        holder.tvAllValidCount.text =
            String.format(context.getString(R.string.all_valid_count), mData!![position].all_valid_count)

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

    inner class ViewHolder(binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNickname: TextView = binding.tvNickName
        val tvAccount: TextView = binding.tvAccount
        val tvLevel: TextView = binding.tvLevel
        val tvAllCount: TextView = binding.tvAllCount
        val tvAllValidCount: TextView = binding.tvAllValidCount
    }

}