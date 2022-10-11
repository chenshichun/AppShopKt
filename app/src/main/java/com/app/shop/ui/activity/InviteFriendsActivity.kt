package com.app.shop.ui.activity

import android.graphics.BitmapFactory
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ShareBean
import com.app.shop.databinding.ActivityInviteFriendsBinding
import com.app.shop.ui.contract.InviteFriendsContract
import com.app.shop.ui.presenter.InviteFriendsPresenter
import com.app.shop.util.ToastUtil
import com.app.shop.util.WeChatShareUtils
import com.app.shop.util.WeChatShareUtils.weChatShareUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader


class InviteFriendsActivity : BaseActivity<ActivityInviteFriendsBinding, InviteFriendsPresenter>(),
    InviteFriendsContract.View {
    private lateinit var shareBean: ShareBean
    override fun getPresenter(): InviteFriendsPresenter {
        return InviteFriendsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        binding.viewHead.tvTitle.text = "邀请赚钱"

        binding.tvHaibao.setOnClickListener {
            XPopup.Builder(this)
                .asImageViewer(
                    null,
                    shareBean.share.inv_img,
                    SmartGlideImageLoader()
                ).show()
        }

        binding.tvCopy.setOnClickListener {
            val weChatShareUtils = WeChatShareUtils.getInstance(this)
            if (weChatShareUtils.isSupportWX) {
                val desc = "来看看新版易货君吧"
                val title = "邀请好友"
                val url = shareBean.share.inv_link
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_login)
                weChatShareUtils.shareUrl(url, title, bitmap, desc, 0)
            } else {
                ToastUtil.showToast("手机上微信版本不支持分享功能")
            }
        }

        mPresenter!!.getShareInfo()
    }

    override fun getShareInfo(mData: BaseNetModel<ShareBean>) {
        shareBean = mData.data!!
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}