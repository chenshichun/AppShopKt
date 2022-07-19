package com.app.shop.manager

import android.content.Context
import android.text.TextUtils
import com.app.shop.base.BaseConstant
import com.app.shop.bean.UserBean
import com.app.shop.util.MmkvUtil
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：账号管理类
 */
class AccountManager {
    companion object {
        private var mAccount: UserBean? = null
        private var mAuthorizationToken: String? = null
        private var applicationContext: Context? = null

        fun init(context: Context?) {
            this.applicationContext = context
            mAccount = MmkvUtil().get(BaseConstant.TAG_USER_BEAN, UserBean::class.java)
            mAuthorizationToken = MmkvUtil().get(BaseConstant.TAG_TOKEN, "") as String?
        }

        fun getAccountInfo(): UserBean? {
            return mAccount
        }

        fun getToken(): String? {
            return mAuthorizationToken
        }

        fun signInToken(token: String) {
            mAuthorizationToken = token
            MmkvUtil().put(BaseConstant.TAG_TOKEN, token)
        }

        fun signIn(account: UserBean) {
            mAccount = account
            MmkvUtil().put(BaseConstant.TAG_USER_BEAN, account)
        }

        fun isLogin(): Boolean {
            return mAccount != null && !TextUtils.isEmpty(mAuthorizationToken)
        }

        fun signOut() {
            //清空账户信息
            mAccount = null
            mAuthorizationToken = ""

            MmkvUtil().put(BaseConstant.TAG_TOKEN, mAuthorizationToken)
            MmkvUtil().put(BaseConstant.TAG_USER_BEAN, mAccount)
        }
    }
}