package com.app.shop.base

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
/**
 * @author ludoven
 */
interface BaseContract {
    interface BasePresenter {
        fun attachView(view: BaseView)
        fun detachView()
        fun cancelAll()
    }

    interface BaseView {
        /**
         * @param flag 用于标记对应接口
         * @param e 错误信息
         */
        fun showError( e: Throwable?)
    }
}
