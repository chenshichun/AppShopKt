package com.app.shop.manager

/**
 * @ProjectName : JeecgBoot
 * @Author : chenshichun
 * @Time : 2020/11/2 19:17
 * @Description : 文件描述
 */
class Constants {
    companion object {
        const val WEB_RESP_CODE_SUCCESS = 0
        const val TOKEN_EXPRIED = 10000
        const val LOGIN_OUT = 402 // 您的账号在其他地方登录，已退出登录

        const val PRIVACT_POLICY_URL = "https://dev.kjmall.cc/d/privacypolicy.txt"
        const val USET_AGREEMENT_URL = "https://dev.kjmall.cc/d/eula.txt"

        const val CATEGORY_TYPE: String = "CATEGORY_TYPE"
        const val CATEGORY_ID: String = "CATEGORY_ID"
        const val CATEGORY_NAME: String = "CATEGORY_NAME"
        const val WEB_URL: String = "WEB_URL"
        const val WEB_TITLE: String = "WEB_TITLE"
        const val ORDER_TPYE: String = "order_type"

        const val GOODS_ID: String = "GOODS_ID"
        const val SEARCH_KEY: String = "SEARCH_KEY"
    }
}