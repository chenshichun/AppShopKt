package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.MyTeamAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityMyTeamBinding
import com.app.shop.ui.contract.MyTeamContract
import com.app.shop.ui.presenter.MyTeamPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：我的团队
 */
class MyTeamActivity : BaseActivity<ActivityMyTeamBinding, MyTeamPresenter>(), MyTeamContract.View {

    private lateinit var myTeamAdapter: MyTeamAdapter

    override fun getPresenter(): MyTeamPresenter {
        return MyTeamPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "我的团队"

        myTeamAdapter = MyTeamAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = myTeamAdapter

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}