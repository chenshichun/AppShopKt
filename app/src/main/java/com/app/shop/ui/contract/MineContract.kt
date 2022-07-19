package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface MineContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getMyInfo(mData: BaseNetModel<UserDataBean>)
    }

    interface Presenter {
        fun getMyInfo()
    }
}