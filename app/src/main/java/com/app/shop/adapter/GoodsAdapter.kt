package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.Prod
import com.app.shop.databinding.ItemGoodsBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class GoodsAdapter(private val context: Context, val mData: List<Prod>?) :
    RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = mData!![position].prod_name
        Glide.with(context)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.yzcdn.cn%2Fupload_files%2F2019%2F11%2F20%2FFhnBVAU31lKPK3JC5CFnPSRn8-a9.jpg%3FimageView2%2F2%2Fw%2F580%2Fh%2F580%2Fq%2F75%2Fformat%2Fjpg&refer=http%3A%2F%2Fimg.yzcdn.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661677814&t=4f7368b0439925fa1b999c90a4fd0f27"/*mData[position].pic*/)
            .into(holder.goodsIv)
        holder.tvIntegral.text = "￥${mData[position].price}"
        holder.tvCountSell.text = "${mData[position].sold_num}人已购买"
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

    inner class ViewHolder(binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsIv: ImageView = binding.ivGoods
        val nameTv: TextView = binding.tvName
        val tvIntegral: TextView = binding.tvIntegral
        val tvCountSell: TextView = binding.tvCountSell
    }

}