package com.app.shop.ui.fragment

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.databinding.FragmentMineBinding
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.manager.AccountManager
import com.app.shop.ui.activity.*
import com.app.shop.ui.contract.MineContract
import com.app.shop.ui.presenter.MinePresenter
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的
 *
 */
class MineFragment : BaseFragment<FragmentMineBinding, MinePresenter>(), MineContract.View,
    View.OnClickListener {

    override fun initView() {
        mPresenter!!.getMyInfo()
        binding.tvShowAllOrder.setOnClickListener(this)
        binding.tvOrder1.setOnClickListener(this)
        binding.tvOrder2.setOnClickListener(this)
        binding.tvOrder3.setOnClickListener(this)
        binding.tvOrder4.setOnClickListener(this)
        binding.tvOrder5.setOnClickListener(this)
        binding.tvCollect.setOnClickListener(this)
        binding.tvAttention.setOnClickListener(this)
        binding.tvOutLogin.setOnClickListener(this)
        binding.tvMerchantSettled.setOnClickListener(this)
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
            R.id.tv_out_login -> {
                AccountManager.signOut()
                Intent(activity, LoginActivity::class.java)
            }
            R.id.tv_merchant_settled -> {// 商户入驻
                startActivity(Intent(activity, MerchantSettledActivity::class.java))
            }
        }
    }

    override fun getMyInfo(mData: BaseNetModel<UserDataBean>) {
        AccountManager.signIn(mData.data!!.user!!)
        context?.let { Glide.with(it).load(mData.data!!.user!!.profile_pic).into(binding.ivHead) }
        binding.tvName.text = mData.data!!.user!!.nick_name
        binding.tvInvCode.text = "邀请码：${mData.data!!.user!!.inv_code}"
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}