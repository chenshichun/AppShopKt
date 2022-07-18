package com.app.shop.ui.activity

import android.view.KeyEvent
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.app.shop.R
import com.app.shop.adapter.OrderAdapter
import com.app.shop.adapter.SearchAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivitySearchBinding
import com.app.shop.room.SearchBean
import com.app.shop.room.SearchDao
import com.app.shop.room.SearchDatabase
import com.app.shop.ui.contract.SearchContract
import com.app.shop.ui.presenter.SearchPresenter
import com.google.android.flexbox.*
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：搜索
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchPresenter>(), SearchContract.View {

    lateinit var searchAdapter: SearchAdapter
    private var searchBeanList = mutableListOf<SearchBean>()
    lateinit var searchDao: SearchDao

    override fun getPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.tvCancel.setOnClickListener {
            finish()
        }

        searchDao = SearchDatabase.dataBase.getSearchDao()

        val layoutManager = FlexboxLayoutManager()
        searchAdapter = SearchAdapter(this, searchBeanList)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.mRecyclerView.layoutManager = layoutManager
        binding.mRecyclerView.adapter = searchAdapter

        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickLisenter {
            override fun onItemClick(position: Int) {

            }

            override fun deleteItem(position: Int) {
                lifecycleScope.launch {
                    searchDao.delete(searchBeanList[position])
                    queryAllData()
                }
            }
        })

        binding.editText.setOnEditorActionListener { view, _, _ ->
            lifecycleScope.launch {
                searchDao.putSearchBean(SearchBean(view.text.toString()))
                queryAllData()
            }
            true
        }
        queryAllData()
    }

    private fun queryAllData() {
        lifecycleScope.launch {
            searchBeanList.clear()
            searchBeanList.addAll(searchDao.getAllSearch()!!)
            searchAdapter.notifyDataSetChanged()
        }
    }
}