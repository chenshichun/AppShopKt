package com.app.shop.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.shop.view.ShowAlertDialog
import com.dylanc.viewbinding.inflateBindingWithGeneric

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
abstract class BaseActivity<VB : ViewBinding, P : BaseContract.BasePresenter> : AppCompatActivity(),
    BaseContract.BaseView {
    protected var mPresenter: P? = null
    protected lateinit var binding: VB
    protected var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getPresenter()
        mPresenter?.attachView(this)
        binding = this.inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    abstract fun getPresenter(): P

    protected abstract fun initView()

    override fun showError(e: Throwable?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.cancelAll()
            mPresenter!!.detachView()
        }
        System.gc()
    }

    open fun showLoadingDialog() {
        if (null == dialog) {
            dialog = ShowAlertDialog.loadingDialog(this)
        }
        dialog!!.show()
    }

    open fun closeLoadingDialog() {
        if (null != dialog) {
            dialog!!.dismiss()
        }
    }

}
