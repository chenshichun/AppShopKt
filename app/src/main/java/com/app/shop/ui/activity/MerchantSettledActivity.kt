package com.app.shop.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.databinding.ActivityMerchantSettledBinding
import com.app.shop.req.MerchantSettledReq
import com.app.shop.ui.contract.MerchantSettledContract
import com.app.shop.ui.presenter.MerchantSettledPresenter
import com.app.shop.view.GlideEngine
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述： 商户入驻
 */
class MerchantSettledActivity :
    BaseActivity<ActivityMerchantSettledBinding, MerchantSettledPresenter>(),
    MerchantSettledContract.View, View.OnClickListener {

    private var lice_img: String? = null
    private var id_card_img1: String? = null
    private var id_card_img2: String? = null
    private var id_card_img3: String? = null
    private var imageType: Int = 1
    private var m_addr_area_id: String? = null
    private var lice_area_id: String? = null
    private var e_type: Int = 1 //线上＝1线下＝2易货＝3
    override fun getPresenter(): MerchantSettledPresenter {
        return MerchantSettledPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "商户入驻"

        binding.viewHead.ivBack.setOnClickListener(this)
        binding.layoutSubjectInformation.iv0.setOnClickListener(this)
        binding.iv1.setOnClickListener(this)
        binding.iv2.setOnClickListener(this)
        binding.iv3.setOnClickListener(this)
        binding.tvAddress.setOnClickListener(this)
        binding.tvSave.setOnClickListener(this)
        binding.tvEType.setOnClickListener(this)
        binding.layoutSubjectInformation.tvLiceAreaFull.setOnClickListener(this)
        binding.layoutSubjectInformation.tvSubjectInformation.setOnClickListener(this)

        binding.mMultiLineRadioGroup.check(1)
        binding.mMultiLineRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == 1) {
                binding.layoutSubjectInformation.layoutSubjectInformation.visibility = View.GONE
                binding.layoutSubjectInformation.llSubjectInformation.visibility = View.GONE
            } else {
                binding.layoutSubjectInformation.tvSubjectInformation.visibility = View.VISIBLE
                binding.layoutSubjectInformation.layoutSubjectInformation.visibility =
                    View.VISIBLE
                binding.layoutSubjectInformation.llSubjectInformation.visibility = View.GONE
            }
        }
    }

    override fun upload(mData: BaseNetModel<UploadBean>) {
        when (imageType) {
            0 -> {
                lice_img = mData.data!!.url
                Glide.with(this@MerchantSettledActivity)
                    .load(lice_img).into(binding.layoutSubjectInformation.iv0)
            }
            1 -> {
                id_card_img1 = mData.data!!.url
                Glide.with(this@MerchantSettledActivity)
                    .load(id_card_img1).into(binding.iv1)
            }
            2 -> {
                id_card_img2 = mData.data!!.url
                Glide.with(this@MerchantSettledActivity)
                    .load(id_card_img2).into(binding.iv2)
            }
            3 -> {
                id_card_img3 = mData.data!!.url
                Glide.with(this@MerchantSettledActivity)
                    .load(id_card_img3).into(binding.iv3)
            }
        }
    }

    override fun merchantApply(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun initAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
        type: Int
    ) {
        showAddressPicker(provinceItems, cityItems, areaItems, type)
    }

    @SuppressLint("CheckResult")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> finish()
            R.id.iv0 -> {
                imageType = 0
                chooseImg()
            }
            R.id.iv1 -> {
                imageType = 1
                chooseImg()
            }
            R.id.iv2 -> {
                imageType = 2
                chooseImg()
            }
            R.id.iv3 -> {
                imageType = 3
                chooseImg()
            }
            R.id.tv_address -> {
                mPresenter!!.initAddressPicker(applicationContext, 0)
            }
            R.id.tv_lice_area_full -> {//  注册地区全名
                mPresenter!!.initAddressPicker(applicationContext, 1)
            }
            R.id.tv_e_type -> {// 商户类型
                val optionsItems = mutableListOf<String>()
                optionsItems.add("线上联盟商家")
                optionsItems.add("线下联盟商家")
                optionsItems.add("易货")

                val build = OptionsPickerBuilder(
                    this
                ) { options1, _, _, _ ->
                    binding.tvEType.text = optionsItems[options1]
                    e_type = options1 + 1
                }.build<String>()
                build.setPicker(optionsItems, null)
                build.show()
            }
            R.id.tv_save -> {
                val merchantSettledReq = MerchantSettledReq()
                merchantSettledReq.m_name = binding.etMName.text.toString()
                merchantSettledReq.legal_name = binding.etLegalName.text.toString()
                merchantSettledReq.service_phone = binding.etServicePhone.text.toString()
                merchantSettledReq.legal_phone = binding.etLegalPhone.text.toString()
                merchantSettledReq.m_addr_area_full = binding.tvAddress.text.toString()
                merchantSettledReq.m_addr_area_id = m_addr_area_id
                merchantSettledReq.m_addr_detail = binding.etMAddrDetail.text.toString()
                merchantSettledReq.m_type = binding.mMultiLineRadioGroup.checkedRadioButtonId - 1
                merchantSettledReq.employee_name = binding.etEmployeeName.text.toString()
                merchantSettledReq.id_type = 0//身份证＝0
                merchantSettledReq.e_type = e_type// 商户类型 线上＝1线下＝2易货＝3
                merchantSettledReq.id_num = binding.etIdNum.text.toString()
                merchantSettledReq.id_card_img1 = id_card_img1
                merchantSettledReq.id_card_img2 = id_card_img2
                merchantSettledReq.id_card_img3 = id_card_img3
                if (binding.mMultiLineRadioGroup.checkedRadioButtonId - 1 != 0) {// 商户类型不是个人
                    merchantSettledReq.c_name =
                        binding.layoutSubjectInformation.etCName.text.toString()
                    merchantSettledReq.lice_num =
                        binding.layoutSubjectInformation.etLiceNum.text.toString()
                    merchantSettledReq.lice_area_full =
                        binding.layoutSubjectInformation.tvLiceAreaFull.text.toString()
                    merchantSettledReq.lice_area_id = lice_area_id
                    merchantSettledReq.lice_area_detail =
                        binding.layoutSubjectInformation.etLiceAreaDetail.text.toString()
                    merchantSettledReq.lice_img = lice_img
                    merchantSettledReq.c_type =
                        if (binding.mMultiLineRadioGroup.checkedRadioButtonId == 1) 0 else 1
                }
                mPresenter!!.merchantApply(merchantSettledReq)
            }
            R.id.tv_subject_information -> {
                binding.layoutSubjectInformation.tvSubjectInformation.visibility = View.GONE
                binding.layoutSubjectInformation.llSubjectInformation.visibility = View.VISIBLE
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    /**
     * 显示地址选择
     */
    private fun showAddressPicker(
        provinceItems: MutableList<AddressInfoPO>,
        cityItems: MutableList<MutableList<AddressInfoPO>>,
        areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
        type: Int
    ) {
        val addressPv =
            OptionsPickerBuilder(this) { options1, options2, options3, _ ->
                //省份
                provinceItems[options1]
                //城市
                cityItems[options1][options2]
                //辖区
                areaItems[options1][options2][options3]

                if (type == 0) {//  商户地址
                    binding.tvAddress.text =
                        provinceItems[options1].name + cityItems[options1][options2].name + areaItems[options1][options2][options3].name
                    m_addr_area_id = areaItems[options1][options2][options3].code

                } else {// 注册地址
                    binding.layoutSubjectInformation.tvLiceAreaFull.text =
                        provinceItems[options1].name + cityItems[options1][options2].name + areaItems[options1][options2][options3].name
                    lice_area_id = areaItems[options1][options2][options3].code
                }
            }
                .setTitleText("地址选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build<AddressInfoPO>()
        addressPv.setPicker(provinceItems, cityItems, areaItems)
        addressPv.show()
    }

    @SuppressLint("CheckResult")
    fun chooseImg() {
        //获取写的权限
        val rxPermission = RxPermissions(this@MerchantSettledActivity)
        rxPermission.requestEach(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
            .subscribe { permission ->
                if (permission.granted) { // 用户已经同意该权限
                    PictureSelector.create(this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setMaxSelectNum(1)
                        .forResult(object : OnResultCallbackListener<LocalMedia?> {
                            override fun onResult(result: ArrayList<LocalMedia?>?) {
                                for (locatMedia in result!!) {
                                    val file = File(locatMedia!!.realPath)
                                    val requestBody = RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        file
                                    )
                                    val fromData = MultipartBody.Part.createFormData(
                                        "file",
                                        file.name,
                                        requestBody
                                    )

                                    val description: RequestBody = RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        "profile_pic"
                                    )
                                    mPresenter!!.upload(fromData, description)
                                }
                            }

                            override fun onCancel() {

                            }
                        })
                } else {
                    Toast.makeText(
                        this@MerchantSettledActivity,
                        "拒绝",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}