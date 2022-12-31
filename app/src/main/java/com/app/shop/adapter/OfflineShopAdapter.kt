package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.LocalStore
import com.app.shop.databinding.ItemOfflineShopBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/11
 * 描述：
 *
 */
class OfflineShopAdapter(private val context: Context, val mData: List<LocalStore>?) :
    RecyclerView.Adapter<OfflineShopAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOfflineShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(mData!![position].shop_type){
            0->holder.ivType.setBackgroundResource(R.drawable.icon_merchant_label)
            1->holder.ivType.setBackgroundResource(R.drawable.zs_icon)
            2->holder.ivType.setBackgroundResource(R.drawable.hg_icon)
        }
        Glide.with(context).load(mData!![position].shop_logo)
            .placeholder(R.drawable.icon_default_pic)
            .error(R.drawable.icon_default_pic).into(holder.ivShop)
        holder.tvAddress.text = String.format(
            context.getString(R.string.address),
            mData[position].province + mData[position].city + mData[position].area + mData[position].shop_address
        )
        holder.tvPhone.text = String.format(context.getString(R.string.phone), mData[position].tel)
        holder.tvName.text = mData[position].shop_name
        holder.tvSales.text =
            String.format(context.getString(R.string.sales), mData[position].sales)
        holder.tvScore.text =
            String.format(context.getString(R.string.score), mData[position].score)

        holder.tvNavigation.setOnClickListener {
            mOnItemClickListener!!.onNavigationClick(position)
        }
        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onNavigationClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemOfflineShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNavigation: TextView = binding.tvNavigation
        val ivShop: ImageView = binding.ivShop
        val tvAddress: TextView = binding.tvAddress
        val tvName: TextView = binding.tvName
        val tvPhone: TextView = binding.tvPhone
        val tvSales: TextView = binding.tvSales
        val tvScore: TextView = binding.tvScore
        val ivType: ImageView = binding.ivType
    }

}