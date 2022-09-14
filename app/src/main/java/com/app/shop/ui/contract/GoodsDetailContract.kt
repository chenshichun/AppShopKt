package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.GoodsBean

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：
 */
interface GoodsDetailContract {
    interface View : BaseContract.BaseView {
        fun prodGet(mData: BaseNetModel<GoodsBean>)
    }

    interface Presenter {
        fun prodGet(id: String)
    }
}