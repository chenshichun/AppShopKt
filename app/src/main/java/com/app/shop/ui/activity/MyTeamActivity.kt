package com.app.shop.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.MyTeamAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ListBean
import com.app.shop.bean.MyTeamBean
import com.app.shop.bean.TeamAllBean
import com.app.shop.databinding.ActivityMyTeamBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.ui.contract.MyTeamContract
import com.app.shop.ui.presenter.MyTeamPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：我的团队
 */
class MyTeamActivity : BaseActivity<ActivityMyTeamBinding, MyTeamPresenter>(), MyTeamContract.View {

    private lateinit var myTeamAdapter: MyTeamAdapter
    private var teamList = arrayListOf<ListBean>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10

    override fun getPresenter(): MyTeamPresenter {
        return MyTeamPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "我的团队"

        myTeamAdapter = MyTeamAdapter(this, teamList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = myTeamAdapter

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            initData()
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            initData()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page++
            mPresenter!!.teamAll(page, size)
        }
        initData()
    }

    private fun initData() {
        mPresenter!!.teamMy()
        mPresenter!!.teamAll(page, size)
    }

    override fun teamMy(mData: BaseNetModel<MyTeamBean>) {
        binding.tvNickName.text =
            String.format(getString(R.string.nickname), mData.data!!.my_info.nick)
        binding.tvPhone.text =
            String.format(getString(R.string.account), mData.data!!.my_info.phone)
        binding.tvLevel.text = String.format(getString(R.string.level), mData.data!!.my_info.level)
        binding.tvDtCount.text =
            String.format(getString(R.string.dt_count), mData.data!!.my_info.dt_count)
        binding.tvDtValidCount.text =
            String.format(getString(R.string.dt_valid_count), mData.data!!.my_info.dt_valid_count)
        binding.tvAllCount.text =
            String.format(getString(R.string.all_count), mData.data!!.my_info.all_count)
        binding.tvAllValidCount.text =
            String.format(getString(R.string.all_valid_count), mData.data!!.my_info.all_valid_count)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun teamAll(mData: BaseNetModel<TeamAllBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.team_info.list.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                teamList.clear()
                teamList.addAll(mData.data!!.team_info.list)
                myTeamAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.team_info.list.isEmpty()) {
                page--
            } else {
                teamList.addAll(mData.data!!.team_info.list)
                myTeamAdapter.notifyDataSetChanged()
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