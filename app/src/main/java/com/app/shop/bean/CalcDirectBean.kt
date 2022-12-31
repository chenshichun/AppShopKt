package com.app.shop.bean

data class CalcDirectBean(
    val calc_result: CalcResult
)

data class CalcResult(
    val cash: String,
    val express_cost: String,
    val point: String,
    val prod_count: String,
    val service_cost: String,
    val total_cash: String,
    val total_cash_with_reward: String,
    val total_cash_with_barter: String,
    val total_cash_with_expend: String
)