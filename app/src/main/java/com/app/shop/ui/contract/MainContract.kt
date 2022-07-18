package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.HotSearchBean

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface MainContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun testView(string: String)
        fun hotSearch(data: BaseNetModel<ArrayList<HotSearchBean>>)
        fun hotNewSearch(data: BaseNetModel<ArrayList<HotSearchBean>>)
    }

    interface Presenter {
        fun testPresenter()
        fun hotSearch()
        fun hotNewSearch()
    }
}