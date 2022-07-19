package com.app.shop.manager

import com.google.gson.annotations.SerializedName

class HttpStatus {
    @SerializedName("code")
    val code = 0

    @SerializedName("msg")
    val msg: String? = null

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    val isCodeInvalid: Boolean
        get() = this.code != Constants.WEB_RESP_CODE_SUCCESS
    val isNeedLogin: Boolean
        get() = this.code == Constants.LOGIN_OUT
}