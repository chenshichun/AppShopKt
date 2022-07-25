package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.PointAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.PlBean
import com.app.shop.bean.PointBean
import com.app.shop.databinding.ActivityPointsDetailsBinding
import com.app.shop.ui.contract.PointsDetailsContract
import com.app.shop.ui.presenter.PointsDetailsPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：积分明细
 *
 */
class PointsDetailsActivity : BaseActivity<ActivityPointsDetailsBinding, PointsDetailsPresenter>(),
    PointsDetailsContract.View {

    private lateinit var pointAdapter: PointAdapter
    private var pointList = mutableListOf<PlBean>()

    override fun getPresenter(): PointsDetailsPresenter {
        return PointsDetailsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "积分明细"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        pointAdapter = PointAdapter(this,pointList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = pointAdapter

        mPresenter!!.listPoint()
    }

    override fun listPoint(mData: BaseNetModel<PointBean>) {
        pointList.addAll(mData.data!!.pl)
        pointAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}