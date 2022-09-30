package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.Addr
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.databinding.ActivityOperationCenterApplyBinding
import com.app.shop.req.ServiceApplyReq
import com.app.shop.ui.contract.OperationCenterApplyContract
import com.app.shop.ui.presenter.OperationCenterApplyPresenter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：运营中心申请
 */
class OperationCenterApplyActivity :
    BaseActivity<ActivityOperationCenterApplyBinding, OperationCenterApplyPresenter>(),
    OperationCenterApplyContract.View {

    private var addr: Addr? = null
    private lateinit var province: String
    private lateinit var city: String
    private lateinit var area: String

    override fun getPresenter(): OperationCenterApplyPresenter {
        return OperationCenterApplyPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "运营中心申请"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvArea.setOnClickListener {
            mPresenter!!.initAddressPicker(this)
        }

        binding.btConfirm.setOnClickListener {
            val serviceApplyReq = ServiceApplyReq(
                binding.etDetailAddress.text.toString(),
                area,
                city,
                binding.etName.text.toString(),
                binding.etPhone.text.toString(),
                province
            )
            mPresenter!!.serviceApply(serviceApplyReq)
        }
    }

    override fun serviceApply(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun initAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>
    ) {
        showAddressPicker(provinceItems, cityItems, areaItems)
    }

    /**
     * 显示地址选择
     */
    @SuppressLint("SetTextI18n")
    private fun showAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
    ) {
        val addressPv =
            OptionsPickerBuilder(this) { options1, options2, options3, _ ->
                //省份
                provinceItems[options1]
                //城市
                cityItems[options1][options2]
                //辖区
                areaItems[options1][options2][options3]

                binding.tvArea.text =
                    provinceItems[options1].name + cityItems[options1][options2].name + areaItems[options1][options2][options3].name
                province = provinceItems[options1].name
                city = cityItems[options1][options2].name
                area = areaItems[options1][options2][options3].name
            }
                .setTitleText("地址选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build<AddressInfoPO>()
        addressPv.setPicker(provinceItems, cityItems, areaItems)
        addressPv.show()
    }


    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}