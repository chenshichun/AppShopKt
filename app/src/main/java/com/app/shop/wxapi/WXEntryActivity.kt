package com.app.shop.wxapi

import android.app.Activity
import android.os.Bundle
import com.app.shop.base.BaseConstant
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * @author chenshichun
 * 创建日期：2022/7/23
 * 描述：
 *
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    val APP_ID: String = BaseConstant.WX_APP_ID //这里写自己的appid
    private var api: IWXAPI? = null

    interface Back {
        fun onWxLoginFiled(errorCode: Int)
        fun onWxLoginSuccess(code: String?, state: String?)
    }

    companion object {
        var authBack: Back? = null
        fun registAuthBack(back: Back) {
            authBack = back
        }

        fun unregistAuthBack() {
            authBack = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, BaseConstant.WX_APP_ID, true)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq?) {
    }

    override fun onResp(p0: BaseResp?) {
        when (p0!!.type) {
            ConstantsAPI.COMMAND_SENDAUTH -> {
                if (authBack != null) {
                    // 0:成功; -1:错误; -2:用户取消;
                    val authResp = p0 as SendAuth.Resp
                    if (authResp.errCode == 0) {
                        authBack!!.onWxLoginSuccess(authResp.code, authResp.state)
                    } else {
                        authBack!!.onWxLoginFiled(p0.errCode)
                    }
                }
                finish()
            }
        }
    }
}