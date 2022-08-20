package com.app.shop.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.app.shop.R
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.FragmentNewMineBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.activity.*
import com.app.shop.ui.contract.MineContract
import com.app.shop.ui.presenter.MinePresenter
import com.app.shop.util.ToastUtil
import com.app.shop.view.GlideEngine
import com.bumptech.glide.Glide
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @author chenshichun
 * 创建日期：2022/8/8
 * 描述：
 *
 */
class MineNewFragment : BaseFragment<FragmentNewMineBinding, MinePresenter>(), MineContract.View,
    View.OnClickListener {
    private lateinit var register: ActivityResultLauncher<Intent>

    override fun getPresenter(): MinePresenter {
        return MinePresenter()
    }

    override fun initView() {
        mPresenter!!.getMyInfo()
        binding.ivHead.setOnClickListener(this)
        binding.tvNickName.setOnClickListener(this)
        binding.tvInvCode.setOnClickListener(this)

        binding.llAttention.setOnClickListener(this)
        binding.llAddress.setOnClickListener(this)
        binding.llScan.setOnClickListener(this)
        binding.llVerified.setOnClickListener(this)
        binding.llMerchantSettled.setOnClickListener(this)
        binding.llSetting.setOnClickListener(this)

        // 注册扫描二维码
        register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val data = it.data
            if (null != data) {
                val bundle: Bundle? = data.extras
                if (bundle!!.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING)
                    Logger.d(result)
                    ToastUtil.showToast(result)
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast("解析二维码失败")
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_head -> {// 设置头像
                headIvEvent()
            }
            R.id.tv_nick_name -> {// 修改昵称
                startActivity(Intent(activity, ModifyNickNameActivity::class.java))
            }
            R.id.tv_inv_code -> {// 邀请码
                //获取系统剪贴板
                val cbm = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cbm.text = AccountManager.getAccountInfo()!!.inv_code
                ToastUtil.showToast("邀请码复制成功")
            }
            R.id.ll_attention -> {// 我的关注
                startActivity(Intent(activity, AttentionActivity::class.java))
            }
            R.id.ll_merchant_settled -> {// 商户入驻
                XPopup.Builder(context)
                    .isDestroyOnDismiss(true)
                    .isDarkTheme(false)
                    .hasShadowBg(true)
                    .moveUpToKeyboard(false)
                    .isCoverSoftInput(true)
                    .asBottomList(
                        "",
                        arrayOf("新商户入驻", "商户修改")
                    ) { position, _ ->
                        when (position) {
                            0 -> {// 新商户入驻
                                startActivity(Intent(activity, MerchantSettledActivity::class.java))
                            }
                            1 -> {// 商户修改
                            }
                        }
                    }.show()
            }
            R.id.ll_scan -> {
                RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .subscribe { granted: Boolean ->
                        if (granted) { // 用户已经同意该权限
                            register.launch(Intent(activity, CaptureActivity::class.java))
                        } else {
                            Toast.makeText(
                                activity,
                                "拒绝",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            R.id.ll_address->{
                startActivity(Intent(activity, AddressListActivity::class.java))
            }
            R.id.ll_verified -> {
                startActivity(Intent(activity, VerifiedActivity::class.java))
            }
            R.id.ll_setting->{
                startActivity(Intent(activity, SettingActivity::class.java))
            }
        }
    }

    override fun upload(mData: BaseNetModel<UploadBean>) {
        ToastUtil.showToast("头像上传成功")
    }

    override fun getMyInfo(mData: BaseNetModel<UserDataBean>) {
        AccountManager.signIn(mData.data!!.user!!)
        context?.let { Glide.with(it).load(mData.data!!.user!!.profile_pic).into(binding.ivHead) }
        binding.tvNickName.text = mData.data!!.user!!.nick_name
        binding.tvAccount.text = mData.data!!.user!!.phone
        binding.tvInvCode.text = "邀请码：${mData.data!!.user!!.inv_code}"
        binding.ivQrCode.setImageBitmap(
            CodeUtils.createImage(
                mData.data!!.user!!.inv_code,
                200,
                200,
                null
            )
        )
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    private fun headIvEvent() {
        XPopup.Builder(context)
            .isDarkTheme(false)
            .hasShadowBg(true)
            .moveUpToKeyboard(false)
            .isCoverSoftInput(true)
            .asBottomList(
                "", arrayOf("查看头像", "从相册选择图片")
            ) { position, _ ->
                when (position) {
                    0 -> {// 查看图片
                        XPopup.Builder(context)
                            .asImageViewer(
                                binding.ivHead,
                                AccountManager.getAccountInfo()!!.profile_pic,
                                SmartGlideImageLoader()
                            ).show()
                    }
                    1 -> {// 更换头像
                        chooseHeadImg()
                    }
                }
            }.show()
    }

    @SuppressLint("CheckResult")
    fun chooseHeadImg() {
        //获取写的权限
        val rxPermission = activity?.let { RxPermissions(it) }
        rxPermission!!.requestEach(
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
                        activity,
                        "拒绝",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}