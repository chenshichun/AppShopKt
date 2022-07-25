package com.app.shop.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.app.shop.R
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.FragmentMineBinding
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
import com.tbruyelle.rxpermissions2.RxPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的
 *
 */
class MineFragment : BaseFragment<FragmentMineBinding, MinePresenter>(), MineContract.View,
    View.OnClickListener {

    override fun initView() {
        mPresenter!!.getMyInfo()
        binding.ivHead.setOnClickListener(this)
        binding.tvNickName.setOnClickListener(this)
        binding.tvAccountSecurity.setOnClickListener(this)
        binding.tvShowAllOrder.setOnClickListener(this)
        binding.tvInvCode.setOnClickListener(this)
        binding.tvOrder1.setOnClickListener(this)
        binding.tvOrder2.setOnClickListener(this)
        binding.tvOrder3.setOnClickListener(this)
        binding.tvOrder4.setOnClickListener(this)
        binding.tvOrder5.setOnClickListener(this)
        binding.tvCollect.setOnClickListener(this)
        binding.tvAttention.setOnClickListener(this)
        binding.tvOutLogin.setOnClickListener(this)
        binding.tvMerchantSettled.setOnClickListener(this)
        binding.llPointsDetails.setOnClickListener(this)
        binding.tvAboutUs.setOnClickListener(this)
    }

    override fun getPresenter(): MinePresenter {
        return MinePresenter()
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
            R.id.ll_points_details -> startActivity(
                Intent(
                    activity,
                    PointsDetailsActivity::class.java
                )
            )
            R.id.tv_show_all_order -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_1 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_2 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_3 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_4 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_order_5 -> startActivity(Intent(activity, OrderActivity::class.java))
            R.id.tv_collect -> startActivity(Intent(activity, CollectActivity::class.java))
            R.id.tv_attention -> startActivity(Intent(activity, AttentionActivity::class.java))
            R.id.tv_account_security -> startActivity(
                Intent(
                    activity,
                    AccountSecurityActivity::class.java
                )
            )
            R.id.tv_out_login -> {
                val builder = activity?.let { AlertDialog.Builder(it) }!!
                builder.setMessage("您确定要退出登录吗？")
                builder.setTitle("提示")
                builder.setPositiveButton("确定") { _, _ ->
                    AccountManager.signOut()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.nav_host_fragment_activity_main
                    )
                        .navigate(R.id.navigation_home)
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }
            R.id.tv_merchant_settled -> {// 商户入驻
                startActivity(Intent(activity, MerchantSettledActivity::class.java))
            }
            R.id.tv_about_us -> startActivity(Intent(activity, AboutUsActivity::class.java))
        }
    }

    /*
    * 图片上传结果
    * */
    override fun upload(mData: BaseNetModel<UploadBean>) {
        ToastUtil.showToast("头像上传成功")
    }

    override fun getMyInfo(mData: BaseNetModel<UserDataBean>) {
        AccountManager.signIn(mData.data!!.user!!)
        context?.let { Glide.with(it).load(mData.data!!.user!!.profile_pic).into(binding.ivHead) }
        binding.tvNickName.text = mData.data!!.user!!.nick_name
        binding.tvInvCode.text = "邀请码：${mData.data!!.user!!.inv_code}"
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