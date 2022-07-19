package com.app.shop.base

import com.app.shop.BuildConfig


/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
class BaseConstant {
    companion object {
        const val TAG_USER_BEAN = "tag_user_bean"
        const val ACCESS_TOKEN = "Authorization" //X-Access-Token
        const val TAG_TOKEN = "tag_user_token"
        const val HOST: String = BuildConfig.BASE_URL;
    }
}