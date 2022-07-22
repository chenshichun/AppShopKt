package com.app.shop.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOrderBinding
import com.app.shop.ui.contract.OrderContract
import com.app.shop.ui.fragment.OrderFragment
import com.app.shop.ui.presenter.OrderPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的订单
 *
 */
class OrderActivity : BaseActivity<ActivityOrderBinding, OrderPresenter>(), OrderContract.View {

    private val mTitles = arrayOf("待付款", "待发货", "待收货", "待评价")
    private var mFragments = ArrayList<Fragment>()
    private lateinit var mAdapter: MyPagerAdapter

    override fun getPresenter(): OrderPresenter {
        return OrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "我的订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        for (title in mTitles) {
            mFragments.add(OrderFragment())
        }
        mAdapter = MyPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = mAdapter
        binding.slidingTabLayout.setViewPager(binding.viewPager)
        binding.slidingTabLayout.showMsg(3, 5)
        binding.slidingTabLayout.setMsgMargin(3, 0f, 10f)
    }

    private inner class MyPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}