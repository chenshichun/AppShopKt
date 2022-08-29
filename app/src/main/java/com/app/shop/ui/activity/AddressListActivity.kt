package com.app.shop.ui.activity

import android.content.Intent
import androidx.appcompat.app.AlertDialog
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

    private lateinit var addressAdapter: AddressAdapter

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
        addressAdapter.setOnItemClickListener(object : AddressAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
            }

            override fun onItemDeleteClick(position: Int) {// 删除地址
                val builder = AlertDialog.Builder(this@AddressListActivity)
                builder.setMessage(
                    "确定要删除该地址？"
                )
                builder.setTitle("删除地址")
                builder.setPositiveButton("确定") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }

            override fun onItemEditClick(position: Int) {// 编辑
            }

            override fun onDefaultCheck(position: Int) {// 默认地址
            }

        })

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