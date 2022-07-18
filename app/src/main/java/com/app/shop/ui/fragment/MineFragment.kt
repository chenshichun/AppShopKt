package com.app.shop.ui.fragment

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.databinding.FragmentMineBinding
import com.app.shop.base.BaseFragment
import com.app.shop.ui.activity.AttentionActivity
import com.app.shop.ui.activity.CollectActivity
import com.app.shop.ui.activity.OrderActivity
import com.app.shop.ui.contract.MineContract
import com.app.shop.ui.presenter.MinePresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的
 *
 */
class MineFragment : BaseFragment<FragmentMineBinding, MinePresenter>(), MineContract.View,
    View.OnClickListener {

    override fun initView() {
        binding.tvShowAllOrder.setOnClickListener(this)
        binding.tvOrder1.setOnClickListener(this)
        binding.tvOrder2.setOnClickListener(this)
        binding.tvOrder3.setOnClickListener(this)
        binding.tvOrder4.setOnClickListener(this)
        binding.tvOrder5.setOnClickListener(this)
        binding.tvCollect.setOnClickListener(this)
        binding.tvAttention.setOnClickListener(this)
    }

    override fun getPresenter(): MinePresenter {
        return MinePresenter()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_show_all_order -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_1 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_2 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_3 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_4 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_5 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_collect -> startActivity(Intent(activity, CollectActivity::class.java))
            R.id.tv_attention -> startActivity(Intent(activity, AttentionActivity::class.java))
        }
    }
}