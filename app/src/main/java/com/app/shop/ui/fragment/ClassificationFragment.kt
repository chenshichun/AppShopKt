package com.app.shop.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.PrimaryClassificationAdapter
import com.app.shop.adapter.SecondaryClassificationAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CateBean
import com.app.shop.bean.ChildrenBean
import com.app.shop.bean.ClassificationBean
import com.app.shop.bean.event.CateEvent
import com.app.shop.databinding.FragmentClassificationBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.manager.Constants
import com.app.shop.ui.activity.CategoryListActivity
import com.app.shop.ui.activity.MessageActivity
import com.app.shop.ui.activity.SearchActivity
import com.app.shop.ui.contract.ClassificationContract
import com.app.shop.ui.presenter.ClassificationPresenter
import com.app.shop.util.IntentUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
    private lateinit var loadService: LoadService<Any>

    private var cateBeanList = mutableListOf<CateBean>()
    private var childrenBeanList = mutableListOf<ChildrenBean>()

    override fun getPresenter(): ClassificationPresenter {
        return ClassificationPresenter()
    }

    override fun initView() {
        mPrimaryClassificationAdapter =
            activity?.let { PrimaryClassificationAdapter(it, cateBeanList) }!!
        binding.leftRecycleview.layoutManager = LinearLayoutManager(activity)
        binding.leftRecycleview.adapter = mPrimaryClassificationAdapter
        mPrimaryClassificationAdapter.setOnItemClickListener(object :
            PrimaryClassificationAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                for (cateBean in cateBeanList) {
                    cateBean.isCheck = false
                }
                cateBeanList[position].isCheck = true
                mPrimaryClassificationAdapter.notifyDataSetChanged()

                childrenBeanList.clear()
                childrenBeanList.addAll(cateBeanList[position].children)
                mSecondaryClassificationAdapter.notifyDataSetChanged()
                binding.tvCateName.text = cateBeanList[position].category_name
            }
        })

        mSecondaryClassificationAdapter =
            activity?.let { SecondaryClassificationAdapter(it, childrenBeanList) }!!
        binding.rightRecycleview.layoutManager = GridLayoutManager(activity, 3)
        binding.rightRecycleview.adapter = mSecondaryClassificationAdapter
        mSecondaryClassificationAdapter.setOnItemClickListener(object :
            SecondaryClassificationAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constants.CATEGORY_ID, childrenBeanList[position].category_id)
                bundle.putString(Constants.CATEGORY_NAME, childrenBeanList[position].category_name)
                IntentUtil.get()!!.goActivity(activity, CategoryListActivity::class.java, bundle)
            }
        })

        binding.ivMessage.setOnClickListener{
            IntentUtil.get()!!.goActivity(activity, MessageActivity::class.java)
        }

        loadService = LoadSir.getDefault().register(binding.llCate) {
            initData()
        }

        initData()

        binding.tvSearch.setOnClickListener{
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    private fun initData() {
        mPresenter!!.getCateList()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getCateList(mData: BaseNetModel<ClassificationBean>) {
        if (mData.data!!.cate.isEmpty()) {
            loadService.showCallback(EmptyCallBack::class.java)
        } else {
            loadService.showSuccess()
            cateBeanList.clear()
            cateBeanList.addAll(mData.data!!.cate)
            cateBeanList[0].isCheck = true
            mPrimaryClassificationAdapter.notifyDataSetChanged()
            childrenBeanList.clear()
            childrenBeanList.addAll(mData.data!!.cate[0].children)
            mSecondaryClassificationAdapter.notifyDataSetChanged()
            binding.tvCateName.text = cateBeanList[0].category_name
        }
    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeCate(position: CateEvent) {
        for (cateBean in cateBeanList) {
            cateBean.isCheck = false
        }
        cateBeanList[position.position].isCheck = true
        mPrimaryClassificationAdapter.notifyDataSetChanged()

        childrenBeanList.clear()
        childrenBeanList.addAll(cateBeanList[position.position].children)
        mSecondaryClassificationAdapter.notifyDataSetChanged()
        binding.tvCateName.text = cateBeanList[position.position].category_name
    }
}