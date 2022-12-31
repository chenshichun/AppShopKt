package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.Addr
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.databinding.ActivityAddAddressBinding
import com.app.shop.req.AddrReq
import com.app.shop.ui.contract.AddAddressContract
import com.app.shop.ui.presenter.AddAddressPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.KeyboardUtil
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：新建地址
 */
class AddAddressActivity : BaseActivity<ActivityAddAddressBinding, AddAddressPresenter>(),
    AddAddressContract.View {

    private var addr: Addr? = null
    private lateinit var province: String
    private lateinit var city: String
    private lateinit var area: String

    override fun getPresenter(): AddAddressPresenter {
        return AddAddressPresenter()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        addr = IntentUtil.getParcelableExtra<Addr>(this)
        if (addr == null) {
            binding.viewHead.tvTitle.text = "添加地址"
        } else {
            binding.viewHead.tvTitle.text = "修改地址"
            binding.etName.setText(addr!!.receiver)
            binding.etPhone.setText(addr!!.mobile)
            binding.tvArea.text = addr!!.province + addr!!.city + addr!!.area
            binding.etDetailAddress.setText(addr!!.addr)
            binding.switchBtn.isChecked = addr!!.is_default
            province = addr!!.province.toString()
            city = addr!!.city.toString()
            area = addr!!.area.toString()
        }

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvArea.setOnClickListener {
            KeyboardUtil.hideKeyboard(this)
            mPresenter!!.initAddressPicker(this@AddAddressActivity)
        }

        binding.btConfirm.setOnClickListener {
            if (addr == null) {
                var addrReq = AddrReq(
                    "",
                    binding.etName.text.toString(),
                    province,
                    city,
                    area,
                    binding.etDetailAddress.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.switchBtn.isChecked
                )
                mPresenter!!.addrSave(addrReq)
            } else {
                var addrReq = AddrReq(
                    addr!!.addr_id!!,
                    binding.etName.text.toString(),
                    province,
                    city,
                    area,
                    binding.etDetailAddress.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.switchBtn.isChecked
                )
                mPresenter!!.addrSave(addrReq)
            }
        }
    }

    override fun initAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
    ) {
        showAddressPicker(provinceItems, cityItems, areaItems)
    }

    override fun addrSave(mData: BaseNetModel<Any>) {
        finish()
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