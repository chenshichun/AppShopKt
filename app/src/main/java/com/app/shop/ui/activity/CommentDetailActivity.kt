package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.CommentAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CommInfo
import com.app.shop.bean.CommentBean
import com.app.shop.databinding.ActivityCommentDetailBinding
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.CommentDetailContract
import com.app.shop.ui.presenter.CommentDetailPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：评论详情
 */
class CommentDetailActivity : BaseActivity<ActivityCommentDetailBinding, CommentDetailPresenter>(),
    CommentDetailContract.View {

    private lateinit var commentAdapter: CommentAdapter
    private var commentInfoList = mutableListOf<CommInfo>()
    private lateinit var id: String

    override fun getPresenter(): CommentDetailPresenter {
        return CommentDetailPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "评论详情"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        commentAdapter = CommentAdapter(this, commentInfoList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = commentAdapter

        id = intent.getStringExtra(Constants.GOODS_ID).toString();
        mPresenter!!.prodCommAll(id)
    }

    override fun prodCommAll(mData: BaseNetModel<CommentBean>) {
        commentInfoList.clear()
        commentInfoList.addAll(mData.data!!.comm_info)
        commentAdapter.notifyDataSetChanged()
        binding.tvAll.text = String.format(getString(R.string.all), mData.data!!.all_rows)
        binding.rbCommentScore.rating = mData.data!!.avg_score.toFloat()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}