package com.app.shop.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.PrimaryClassificationAdapter
import com.app.shop.adapter.SecondaryClassificationAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentClassificationBinding
import com.app.shop.ui.contract.ClassificationContract
import com.app.shop.ui.presenter.ClassificationPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：分类
 *
 */
class ClassificationFragment :
    BaseFragment<FragmentClassificationBinding, ClassificationPresenter>(),
    ClassificationContract.View {

    lateinit var mPrimaryClassificationAdapter: PrimaryClassificationAdapter
    lateinit var mSecondaryClassificationAdapter: SecondaryClassificationAdapter

    override fun getPresenter(): ClassificationPresenter {
        return ClassificationPresenter()
    }

    override fun initView() {
        mPrimaryClassificationAdapter = activity?.let { PrimaryClassificationAdapter(it, null) }!!
        binding.leftRecycleview.layoutManager = LinearLayoutManager(activity)
        binding.leftRecycleview.adapter = mPrimaryClassificationAdapter

        mSecondaryClassificationAdapter =
            activity?.let { SecondaryClassificationAdapter(it, null) }!!
        binding.rightRecycleview.layoutManager = GridLayoutManager(activity, 3)
        binding.rightRecycleview.adapter = mSecondaryClassificationAdapter
        mSecondaryClassificationAdapter.setOnItemClickListener(object :
            SecondaryClassificationAdapter.OnItemClickLisenter {
            override fun onItemClick(position: Int) {
            }
        })
    }
}