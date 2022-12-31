package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.WalletDetailBean

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
interface WalletDetailsContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getWalletDetail(mData: BaseNetModel<WalletDetailBean>)
    }

    interface Presenter {
        fun getWalletDetail(
            page: Int,
            size: Int
        )
    }
}