package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.ReservationOrderAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityReservationOrderBinding
import com.app.shop.ui.contract.ReservationOrderContract
import com.app.shop.ui.presenter.ReservationOrderPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：预约订单
 */
class ReservationOrderActivity :
    BaseActivity<ActivityReservationOrderBinding, ReservationOrderPresenter>(),
    ReservationOrderContract.View {

    private lateinit var reservationOrderAdapter: ReservationOrderAdapter
    override fun getPresenter(): ReservationOrderPresenter {
        return ReservationOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "预约订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        reservationOrderAdapter = ReservationOrderAdapter(null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = reservationOrderAdapter
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}