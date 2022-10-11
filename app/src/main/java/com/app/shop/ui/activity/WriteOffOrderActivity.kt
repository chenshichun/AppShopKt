package com.app.shop.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WriteOffOrderAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CashDetailBean
import com.app.shop.bean.VerifyBean
import com.app.shop.bean.VerifyListBean
import com.app.shop.databinding.ActivityWriteOffOrderBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.req.OrderIdReq
import com.app.shop.ui.contract.WriteOffOrderContract
import com.app.shop.ui.presenter.WriteOffOrderPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：核销订单
 */
class WriteOffOrderActivity : BaseActivity<ActivityWriteOffOrderBinding, WriteOffOrderPresenter>(),
    WriteOffOrderContract.View {

    private lateinit var writeOffOrderAdapter: WriteOffOrderAdapter
    private var verifyListBean = mutableListOf<VerifyBean>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10

    override fun getPresenter(): WriteOffOrderPresenter {
        return WriteOffOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "核销订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        writeOffOrderAdapter = WriteOffOrderAdapter(this, verifyListBean)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = writeOffOrderAdapter

        writeOffOrderAdapter.setOnItemClickListener(object :
            WriteOffOrderAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

            override fun onDeteleClick(position: Int) {
                val builder = AlertDialog.Builder(this@WriteOffOrderActivity)
                builder.setMessage("确定删除？")
                builder.setTitle("删除订单")
                builder.setPositiveButton("确定") { dialog, _ ->
                    val orderIdReq = OrderIdReq(verifyListBean[position].order_id)
                    mPresenter!!.orderDelete(orderIdReq)
                    dialog.dismiss()
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }

        })
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

    private fun initData() {
        mPresenter!!.getVerifyList(page, size)
    }

    override fun getVerifyList(mData: BaseNetModel<VerifyListBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.orders.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                verifyListBean.clear()
                verifyListBean.addAll(mData.data!!.orders)
                writeOffOrderAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.orders.isEmpty()) {
                page--
            } else {
                verifyListBean.addAll(mData.data!!.orders)
                writeOffOrderAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun orderDelete(mData: BaseNetModel<Any>) {
        mPresenter!!.getVerifyList(page, size)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}