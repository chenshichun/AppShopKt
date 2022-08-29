package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemAddressBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class AddressAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = "cuckoo"
        holder.tvPhone.text = "15068156432"
        holder.tvAddress.text = "浙江省义乌市苏溪镇123号大马路拉面"

        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position)
        }

        holder.tvDelete.setOnClickListener {
            mOnItemClickListener!!.onItemDeleteClick(position)
        }

        holder.tvEdit.setOnClickListener {
            mOnItemClickListener!!.onItemEditClick(position)
        }

        holder.checkBox.setOnClickListener {
            mOnItemClickListener!!.onDefaultCheck(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemDeleteClick(position: Int)
        fun onItemEditClick(position: Int)
        fun onDefaultCheck(position: Int)
    }

    override fun getItemCount(): Int {
        return 15
    }

    inner class ViewHolder(binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvName
        val checkBox: CheckBox = binding.checkbox
        val tvDelete: TextView = binding.tvDelete
        val tvEdit: TextView = binding.tvEdit
        val tvPhone: TextView = binding.tvPhone
        val tvAddress: TextView = binding.tvAddress
    }

}