package com.app.shop.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.shop.R
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.gyf.immersionbar.ktx.immersionBar

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
}
