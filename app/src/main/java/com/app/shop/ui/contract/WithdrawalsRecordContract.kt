package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CashDetailListBean

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
interface WithdrawalsRecordContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getCashdetail(mData: BaseNetModel<CashDetailListBean>)
    }

    interface Presenter {
        fun getCashdetail(
            page: Int,
            size: Int
        )
    }
}