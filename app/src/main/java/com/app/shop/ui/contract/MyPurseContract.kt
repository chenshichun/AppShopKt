package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.WalletBean

/**
 * @author chenshichun
 * 创建日期：2022/8/22
 * 描述：
 *
 */
interface MyPurseContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun walletInfo(mData: BaseNetModel<WalletBean>)
    }

    interface Presenter {
        fun walletInfo()
    }
}