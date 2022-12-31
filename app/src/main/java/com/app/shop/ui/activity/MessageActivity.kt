package com.app.shop.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityMessageBinding
import com.app.shop.ui.contract.MessageContract
import com.app.shop.ui.fragment.OrderFragment
import com.app.shop.ui.fragment.UserMessageFragment
import com.app.shop.ui.presenter.MessagePresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：消息
 */
class MessageActivity : BaseActivity<ActivityMessageBinding, MessagePresenter>(),
    MessageContract.View {

    private val mTitles = arrayOf("用户消息", "订单消息")
    private var mFragments = ArrayList<Fragment>()
    private lateinit var mAdapter: MyPagerAdapter

    override fun getPresenter(): MessagePresenter {
        return MessagePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "消息"

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        for (title in mTitles) {
            mFragments.add(UserMessageFragment())
        }

        mAdapter = MyPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = mAdapter
        binding.slidingTabLayout.setViewPager(binding.viewPager)
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
    }

    override fun hideLoading() {
    }
}