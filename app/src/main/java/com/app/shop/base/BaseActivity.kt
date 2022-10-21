package com.app.shop.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 */
abstract class BaseActivity<VB : ViewBinding, P : BaseContract.BasePresenter> : AppCompatActivity(),
    BaseContract.BaseView {
    protected var mPresenter: P? = null
    protected lateinit var binding: VB
    private var loadingPopup: BasePopupView? = null
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

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.cancelAll()
            mPresenter!!.detachView()
        }
        System.gc()
    }

    open fun showLoadingDialog() {
        if (null == loadingPopup) {
            loadingPopup = XPopup.Builder(this).hasShadowBg(false)
                .asLoading()
        }
        loadingPopup!!.show()
    }

    open fun closeLoadingDialog() {
        if (null != loadingPopup) {
            loadingPopup!!.dismiss()
        }
    }

}
