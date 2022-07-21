package com.app.shop.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.databinding.ActivityMerchantSettledBinding
import com.app.shop.req.MerchantSettledReq
import com.app.shop.ui.contract.MerchantSettledContract
import com.app.shop.ui.presenter.MerchantSettledPresenter
import com.app.shop.view.GlideEngine
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.orhanobut.logger.Logger
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

    private var id_card_img1: String? = null
    private var id_card_img2: String? = null
    private var id_card_img3: String? = null
    var imageType: Int = 1

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
        binding.iv1.setOnClickListener(this)
        binding.iv2.setOnClickListener(this)
        binding.iv3.setOnClickListener(this)
        binding.tvSave.setOnClickListener(this)
    }

    override fun upload(mData: BaseNetModel<UploadBean>) {
        when (imageType) {
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

    @SuppressLint("CheckResult")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> finish()
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
            R.id.tv_save -> {
                val merchantSettledReq = MerchantSettledReq()
                merchantSettledReq.m_name = binding.etMName.text.toString()
                merchantSettledReq.legal_name = binding.etLegalName.text.toString()
                merchantSettledReq.service_phone = binding.etServicePhone.text.toString()
                merchantSettledReq.legal_phone = binding.etLegalPhone.text.toString()
                merchantSettledReq.m_addr_detail = binding.etMAddrDetail.text.toString()
                merchantSettledReq.m_type = binding.mMultiLineRadioGroup.checkedRadioButtonId - 1
                merchantSettledReq.employee_name = binding.etEmployeeName.text.toString()
                merchantSettledReq.id_type = 0//身份证＝0
                merchantSettledReq.e_type = 1// 商户类型 线上＝1线下＝2易货＝3
                merchantSettledReq.id_num = binding.etIdNum.text.toString()
                merchantSettledReq.id_card_img1 = id_card_img1
                merchantSettledReq.id_card_img2 = id_card_img2
                merchantSettledReq.id_card_img3 = id_card_img3
                mPresenter!!.merchantApply(merchantSettledReq)
            }
        }
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