package com.app.shop.bean

data class WalletBean(
    val wallet: Wallet
)

data class Wallet(
    val accu_cash: String,//累计提现金额
    val avail_cash: String,//可提现金额
    val cash: String //总资产和帐户余额共用
)