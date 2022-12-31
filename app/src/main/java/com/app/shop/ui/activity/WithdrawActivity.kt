package com.app.shop.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WithdrawPriceAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.WithdrawPriceBean
import com.app.shop.databinding.ActivityWithdrawBinding
import com.app.shop.manager.Constants
import com.app.shop.req.CashReq
import com.app.shop.ui.contract.WithdrawContract
import com.app.shop.ui.presenter.WithdrawPresenter
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：提现
 */
class WithdrawActivity : BaseActivity<ActivityWithdrawBinding, WithdrawPresenter>(),
    WithdrawContract.View {

    private lateinit var withdrawPriceAdapter: WithdrawPriceAdapter
    private var withdrawPriceList = mutableListOf<WithdrawPriceBean>()
    private val priceList = listOf("10", "20", "30", "50", "100", "200", "500", "1000")
    private var price: String = ""
    private var position: Int = 0

    override fun getPresenter(): WithdrawPresenter {
        return WithdrawPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "提现"
        price = intent.getStringExtra(Constants.PRICE).toString()
        binding.tvPrice.text = String.format(getString(R.string.price), price)

        for (i in priceList) {
            val withdrawPriceBean = WithdrawPriceBean(false, i)
            withdrawPriceList.add(withdrawPriceBean)
        }

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        withdrawPriceAdapter = WithdrawPriceAdapter(this, withdrawPriceList)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.mRecyclerView.adapter = withdrawPriceAdapter
        withdrawPriceAdapter.setOnItemClickListener(object :
            WithdrawPriceAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(pos: Int) {
                for (withdrawPriceBean in withdrawPriceList) {
                    withdrawPriceBean.isCheck = false
                }
                withdrawPriceList[pos].isCheck = true
                withdrawPriceAdapter.notifyDataSetChanged()
                position = pos
            }

        })

        binding.btConfirm.setOnClickListener {
            if (withdrawPriceList[position].price.toDouble() > price.toDouble()) {
                ToastUtil.showToast("超出可提现金额")
                return@setOnClickListener
            }
            val cashReq = CashReq(withdrawPriceList[position].price)
            mPresenter!!.cashout(cashReq)
        }
    }

    override fun cashout(mData: BaseNetModel<Any>) {

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}