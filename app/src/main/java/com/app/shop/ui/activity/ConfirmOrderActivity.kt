package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.CreateOrderBean
import com.app.shop.databinding.ActivityConfirmOrderBinding
import com.app.shop.ui.contract.ConfirmOrderContract
import com.app.shop.ui.presenter.ConfirmOrderPresenter
import com.app.shop.util.IntentUtil
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：确认订单
 */
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding, ConfirmOrderPresenter>(),
    ConfirmOrderContract.View {

    private var createOrderBean = CreateOrderBean()

    override fun getPresenter(): ConfirmOrderPresenter {
        return ConfirmOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "确认订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        createOrderBean = IntentUtil.getParcelableExtra<CreateOrderBean>(this)!!
        Glide.with(this).load(createOrderBean.pic).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(binding.ivGoods)
        binding.tvGoodsTitle.text = createOrderBean.prod_name
        binding.tvPrice.text =
            String.format(
                getString(if (createOrderBean.ori_point!!.toInt() != 0) R.string.goods_integral else R.string.price),
                if (createOrderBean.ori_point!!.toInt() != 0) createOrderBean.ori_point else createOrderBean.price
            )
        binding.tvAttr.text = String.format(getString(R.string.attr), createOrderBean.attr)
        binding.tvNum.text = createOrderBean.count.toString()

        binding.llAddress.setOnClickListener {
            IntentUtil.get()!!.goActivityResult(this, AddressListActivity::class.java, 100)
        }

        binding.tvConfirm.setOnClickListener {
            IntentUtil.get()!!.goActivity(this, PayOrderActivity::class.java)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}