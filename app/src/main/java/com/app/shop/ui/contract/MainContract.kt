package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UpdateBean

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface MainContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun androidUpdate(mData:BaseNetModel<UpdateBean>)
    }

    interface Presenter {
        fun androidUpdate()
    }
}