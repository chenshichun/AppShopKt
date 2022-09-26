package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.databinding.ActivityModifyNickNameBinding
import com.app.shop.manager.AccountManager
import com.app.shop.req.NickNameReq
import com.app.shop.ui.contract.ModifyNickNameContract
import com.app.shop.ui.presenter.ModifyNickNamePresenter
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述： 修改昵称
 */
class ModifyNickNameActivity :
    BaseActivity<ActivityModifyNickNameBinding, ModifyNickNamePresenter>(),
    ModifyNickNameContract.View {
    override fun getPresenter(): ModifyNickNamePresenter {
        return ModifyNickNamePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "修改昵称"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvOldNickName.text = AccountManager.getAccountInfo()!!.nick_name

        binding.btConfirm.setOnClickListener {
            if (binding.etNewNickName.text.toString().isEmpty()) {
                ToastUtil.showToast("请输入新昵称")
                return@setOnClickListener
            }
            val nickNameReq = NickNameReq(binding.etNewNickName.text.toString())
            mPresenter!!.modifyNickName(nickNameReq)
        }
    }

    override fun modifyNickName(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}