package com.app.shop.ui.fragment

import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentMessageBinding
import com.app.shop.ui.contract.UserMessageContract
import com.app.shop.ui.presenter.UserMessagePresenter

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：
 *
 */
class UserMessageFragment : BaseFragment<FragmentMessageBinding, UserMessagePresenter>(),
    UserMessageContract.View {

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun initView() {
    }

    override fun getPresenter(): UserMessagePresenter {
        return UserMessagePresenter()
    }
}