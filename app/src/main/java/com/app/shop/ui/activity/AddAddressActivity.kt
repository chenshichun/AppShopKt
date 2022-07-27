package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.AddressInfoPO
import com.app.shop.databinding.ActivityAddAddressBinding
import com.app.shop.ui.contract.AddAddressContract
import com.app.shop.ui.presenter.AddAddressPresenter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：新建地址
 */
class AddAddressActivity : BaseActivity<ActivityAddAddressBinding, AddAddressPresenter>(),
    AddAddressContract.View {
    override fun getPresenter(): AddAddressPresenter {
        return AddAddressPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "添加地址"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvArea.setOnClickListener {
            mPresenter!!.initAddressPicker(this@AddAddressActivity)
        }
    }

    override fun initAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
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