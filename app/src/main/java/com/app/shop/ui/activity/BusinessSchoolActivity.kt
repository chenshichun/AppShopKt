package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.BusinessSchoolAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CollegeListBean
import com.app.shop.bean.Prod
import com.app.shop.bean.VideoBean
import com.app.shop.databinding.ActivityBusinessSchoolBinding
import com.app.shop.ui.contract.BusinessSchoolContract
import com.app.shop.ui.presenter.BusinessSchoolPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService

/*
* 商學院
* */
class BusinessSchoolActivity :
    BaseActivity<ActivityBusinessSchoolBinding, BusinessSchoolPresenter>(),
    BusinessSchoolContract.View {
    lateinit var businessSchoolAdapter: BusinessSchoolAdapter
    private var videoBeanList = mutableListOf<VideoBean>()

    override fun getPresenter(): BusinessSchoolPresenter {
        return BusinessSchoolPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "商学院"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        businessSchoolAdapter = BusinessSchoolAdapter(this, videoBeanList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = businessSchoolAdapter

        binding.refreshLayout.setOnRefreshListener {
            mPresenter!!.getCollegeList()
        }

        binding.refreshLayout.setOnLoadMoreListener {
        }
        mPresenter!!.getCollegeList()
    }

    override fun getCollegeList(mData: BaseNetModel<CollegeListBean>) {
        binding.refreshLayout.finishLoadMore()
        binding.refreshLayout.finishRefresh()

        videoBeanList.clear()
        videoBeanList.addAll(mData.data!!.list)
        businessSchoolAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}