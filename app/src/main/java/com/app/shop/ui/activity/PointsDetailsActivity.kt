package com.app.shop.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.PointAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.PlBean
import com.app.shop.bean.PointBean
import com.app.shop.databinding.ActivityPointsDetailsBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.LoadingCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.ui.contract.PointsDetailsContract
import com.app.shop.ui.presenter.PointsDetailsPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：积分明细
 */
class PointsDetailsActivity : BaseActivity<ActivityPointsDetailsBinding, PointsDetailsPresenter>(),
    PointsDetailsContract.View {

    private lateinit var pointAdapter: PointAdapter
    private var pointList = mutableListOf<PlBean>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10

    private var type = 0

    override fun getPresenter(): PointsDetailsPresenter {
        return PointsDetailsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        type = intent.getIntExtra("point_type", 0)
        when (type) {
            0 -> binding.viewHead.tvTitle.text = "赠送积分明细"
            1 -> binding.viewHead.tvTitle.text = "易货积分明细"
            2 -> binding.viewHead.tvTitle.text = "消费积分明细"
        }
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        pointAdapter = PointAdapter(this, pointList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = pointAdapter

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            initData()
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            initData()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page++
            initData()
        }
        initData()
    }

    /*
    * 获取数据
    * */
    private fun initData() {
        loadService.showCallback(LoadingCallback::class.java)
        mPresenter!!.listPoint(page, size)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listPoint(mData: BaseNetModel<PointBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()
        binding.tvPoint.text = mData.data!!.point
        if (page == 1) {
            if (mData.data!!.pl.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                pointList.clear()
                pointList.addAll(mData.data!!.pl)
                pointAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.pl.isEmpty()) {
                page--
            } else {
                pointList.addAll(mData.data!!.pl)
                pointAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}