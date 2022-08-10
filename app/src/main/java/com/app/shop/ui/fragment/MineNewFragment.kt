package com.app.shop.ui.fragment

import android.view.View
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.FragmentNewMineBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.MineContract
import com.app.shop.ui.presenter.MinePresenter
import com.bumptech.glide.Glide
import com.uuzuche.lib_zxing.activity.CodeUtils

/**
 * @author chenshichun
 * 创建日期：2022/8/8
 * 描述：
 *
 */
class MineNewFragment : BaseFragment<FragmentNewMineBinding, MinePresenter>(), MineContract.View,
    View.OnClickListener {

    override fun getPresenter(): MinePresenter {
        return MinePresenter()
    }

    override fun initView() {
        mPresenter!!.getMyInfo()
        binding.ivHead.setOnClickListener(this)
        binding.tvNickName.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
    }

    override fun upload(mData: BaseNetModel<UploadBean>) {
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

}