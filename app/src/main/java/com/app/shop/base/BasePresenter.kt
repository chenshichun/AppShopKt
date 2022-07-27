package com.app.shop.base

import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 */
open class BasePresenter<V: BaseContract.BaseView> : BaseContract.BasePresenter {
    protected var mView: V? = null
    private var weakView: WeakReference<BaseContract.BaseView>? = null
    private var listReqs: MutableList<Disposable> =
        ArrayList()

    override fun attachView(view: BaseContract.BaseView) {
        weakView = WeakReference(view)
        mView = weakView!!.get() as V?
    }

    override fun detachView() {
        if (mView != null) {
            mView = null
            weakView!!.clear()
            weakView = null
        }
    }

    override fun cancelAll() {
        for (disposable in listReqs) {
            disposable.dispose()
        }
    }

    protected fun addRes(disposable: Disposable) {
        listReqs.add(disposable)
    }
}
