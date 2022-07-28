package com.app.shop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
abstract class BaseFragment<VB : ViewBinding, P : BaseContract.BasePresenter> :
    Fragment(), BaseContract.BaseView {
    protected var mPresenter: P? = null

    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    private var loadingPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater)

        return _binding!!.root
    }


    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getPresenter()
        if (mPresenter != null) {
            mPresenter?.attachView(this)
        }

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun getPresenter(): P

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.cancelAll()
            mPresenter?.detachView()
        }
        System.gc()
    }

    open fun showLoadingDialog() {
        if (null == loadingPopup) {
            loadingPopup = XPopup.Builder(activity).asLoading()
        }
        loadingPopup!!.show()
    }

    open fun closeLoadingDialog() {
        if (null != loadingPopup) {
            loadingPopup!!.dismiss()
        }
    }

}
