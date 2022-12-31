package com.app.shop.base

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 */
interface BaseContract {
    interface BasePresenter {
        fun attachView(view: BaseView)
        fun detachView()
        fun cancelAll()
    }

    interface BaseView {
        /**
         * 显示加载中
         */
        fun showLoading()

        /**
         * 隐藏加载
         */
        fun hideLoading()

    }
}
