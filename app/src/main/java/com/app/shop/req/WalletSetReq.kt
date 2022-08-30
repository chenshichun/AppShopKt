package com.app.shop.req

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
class WalletSetReq {
    var payee: String? = null//收款人
    var ali_account: String? = null//支付宝帐号
    var pay_pwd: String? = null//支付密码
    var sms_code: String? = null// 验证码
}