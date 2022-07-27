package com.app.shop.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.AddressAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAddressListBinding
import com.app.shop.ui.contract.AddressListContract
import com.app.shop.ui.presenter.AddressListPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：地址列表
 */
class AddressListActivity : BaseActivity<ActivityAddressListBinding, AddressListPresenter>(),
    AddressListContract.View {

    lateinit var addressAdapter: AddressAdapter

    override fun getPresenter(): AddressListPresenter {
        return AddressListPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "收货地址"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        addressAdapter = AddressAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = addressAdapter
        binding.tvAddAddress.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}