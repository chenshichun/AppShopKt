package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.VerifyBean
import com.app.shop.databinding.ItemWriteOffOrderBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 */
class WriteOffOrderAdapter(private val context: Context, val mData: List<VerifyBean>?) :
    RecyclerView.Adapter<WriteOffOrderAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWriteOffOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvOrderNumber.text =
            String.format(context.getString(R.string.order_num), mData!![position].order_number)
        holder.tvShopName.text = mData[position].shop_name
        holder.tvPhone.text = mData[position].shop_phone
        holder.tvPrice.text =
            String.format(
                context.getString(if (mData[position].point!!.toInt() != 0) R.string.goods_integral else R.string.price),
                if (mData[position].point!!.toInt() != 0) mData[position].point else mData[position].total
            )
        holder.tvTime.text = mData[position].created_at

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }

        holder.tvDetele.setOnClickListener {
            mOnItemClickListener?.onDeteleClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeteleClick(position:Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemWriteOffOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvOrderNumber: TextView = binding.tvOrderNumber
        val tvOrderDetail: TextView = binding.tvOrderDetail
        val tvShopName: TextView = binding.tvShopName
        val tvPhone: TextView = binding.tvPhone
        val tvAddress: TextView = binding.tvAddress
        val tvOrderContext: TextView = binding.tvOrderContext
        val tvPrice: TextView = binding.tvPrice
        val tvTime: TextView = binding.tvTime
        val tvDetele: TextView = binding.tvDetele
    }

}