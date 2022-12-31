package com.app.shop.service

import com.app.shop.bean.*
import com.app.shop.req.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
interface HomeService {

    /*
    * 首页banner
    * */
    @GET("comm/slide/list")
    suspend fun getBannerList(): Response<BaseNetModel<BannerBean>>

    /*
    * 首页商品
    * */
    @GET("prod/all")
    suspend fun getProdHomeData(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<ProdBean>>

    /*
    * 精选商品
    * */
    @GET("prod/featured")
    suspend fun getProdFeaturedData(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<BaseNetModel<ProdBean>>

    /*
    * 推荐商品
    * */
    @GET("prod/recommend")
    suspend fun getProdRecommendData(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<BaseNetModel<ProdBean>>

    /*
    * 分类数据
    * */
    @GET("comm/cate/list")
    suspend fun getCateList(
    ): Response<BaseNetModel<ClassificationBean>>

    /*
    * 指定分类下的数据
    * */
    @GET("prod/by_cate_id")
    suspend fun getCateByIdData(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("cate_id") cateId: String,
        @Query("sort") sort: String
    ): Response<BaseNetModel<ProdBean>>

    /*
    * 商品搜索
    * */
    @GET("prod/home/search")
    suspend fun getSearchData(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
        @Query("keywords") cateId: String,
    ): Response<BaseNetModel<ProdBean>>

    /*
    * 购物车
    * */
    @GET("user/cart/list")
    suspend fun getCartData(
    ): Response<BaseNetModel<CartBean>>

    /*
    * 添加购物车
    * */
    @POST("user/cart/add")
    suspend fun cartAdd(@Body cartAddReq: CartAddReq): Response<BaseNetModel<Any>>

    /*
    * 删除购物车
    * */
    @POST("user/cart/del")
    suspend fun cartDel(@Body cartReq: CartReq): Response<BaseNetModel<Any>>

    /*
    * 用户信息
    * */
    @GET("user/myinfo")
    suspend fun getMyInfo(): Response<BaseNetModel<UserDataBean>>

    /*
    * 商户入驻
    * */
    @POST("user/merchant/apply")
    suspend fun merchantApply(@Body merchantSettledReq: MerchantSettledReq): Response<BaseNetModel<Any>>

    /*
    * 上传文件
    * */
    @Multipart
    @PUT("comm/upload")
    suspend fun upload(
        @Part file: MultipartBody.Part,
        @Part("usage") description: RequestBody
    ): Response<BaseNetModel<UploadBean>>

    /*
    * 签到
    * */
    @GET("user/sign")
    suspend fun sign(): Response<BaseNetModel<Any>>

    /*
    * 积分明细
    * */
    @GET("user/list/point")
    suspend fun listPoint(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: String
    ): Response<BaseNetModel<PointBean>>

    /*
    * 实名认证
    * */
    @POST("user/cert")
    suspend fun cert(@Body certReq: CertReq): Response<BaseNetModel<Any>>

    /*
    * 我的钱包
    * */
    @GET("user/wallet/info")
    suspend fun walletInfo(): Response<BaseNetModel<WalletBean>>

    /*
    * 提现账号
    * */
    @POST("user/wallet/set")
    suspend fun walletSet(@Body walletSetReq: WalletSetReq): Response<BaseNetModel<Any>>

    /*
    * 我的团队头部信息
    * */
    @GET("user/team/my")
    suspend fun teamMy(): Response<BaseNetModel<MyTeamBean>>

    /*
    * 团队页面中直属会员信息
    * */
    @GET("user/team/all")
    suspend fun teamAll(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<TeamAllBean>>

    /*
    * 商品详情
    * */
    @GET("prod/get")
    suspend fun prodGet(@Query("id") id: String): Response<BaseNetModel<GoodsBean>>

    /*
    * 商品评论
    * */
    @GET("prod/comm/all")
    suspend fun prodCommAll(@Query("id") id: String): Response<BaseNetModel<CommentBean>>

    /*
    * 新增地址
    * */
    @POST("user/addr/save")
    suspend fun addrSave(@Body addrReq: AddrReq): Response<BaseNetModel<Any>>

    /*
    * 删除地址
    * */
    @POST("user/addr/del")
    suspend fun addrDel(@Body addrIdReq: AddrIdReq): Response<BaseNetModel<Any>>

    /*
    * 地址列表
    * */
    @GET("user/addr/list")
    suspend fun addrList(): Response<BaseNetModel<AddressBean>>

    /*
    * 设置默认地址
    * */
    @POST("user/addr/default/set")
    suspend fun addrDefultSet(@Body addrIdReq: AddrIdReq): Response<BaseNetModel<Any>>

    /*
    * 获取默认地址
    * */
    @GET("user/addr/default/get")
    suspend fun addrDefault(): Response<BaseNetModel<DefaultDaarBean>>

    /*
    *  单商品生成订单
    * */
    @POST("order/submit/direct")
    suspend fun orderSubmit(@Body createOrderReq: CreateOrderReq): Response<BaseNetModel<OrderIdBean>>

    /*
    * 购物车生成订单
    * */
    @POST("order/submit/cart")
    suspend fun orderSubmitCart(@Body cartIdReq: CartIdReq): Response<BaseNetModel<CartOrderBean>>

    /*
    *  订单列表
    * */
    @GET("order/list")
    suspend fun orderList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("status") status: Int,
    ): Response<BaseNetModel<OrderListBean>>

    /*
    * 店铺收藏
    * */
    @POST("user/store/add")
    suspend fun storeAdd(@Body storeIdReq: StoreIdReq): Response<BaseNetModel<Any>>

    /*
    * 取消店铺收藏
    * */
    @POST("user/store/del")
    suspend fun storeDel(@Body storeIdReq: StoreIdReq): Response<BaseNetModel<Any>>

    /*
    * 获取收藏列表
    * */
    @GET("user/store/list")
    suspend fun storeList(): Response<BaseNetModel<SotreListBean>>

    /*
    * 修改昵称
    * */
    @POST("user/update")
    suspend fun modifyNickName(@Body nickNameReq: NickNameReq): Response<BaseNetModel<Any>>

    /*
    * 本地商家列表
    * */
    @GET("store/list/local")
    suspend fun storeLocalList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("dist") dist: Int,
        @Query("search_name") search_name: String
    ): Response<BaseNetModel<LocatStoreListBean>>

    /*
    * 运营中心
    * */
    @GET("store/list/service")
    suspend fun storeServiceList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("dist") dist: Int,
        @Query("search_name") search_name: String
    ): Response<BaseNetModel<ServiceStoreListBean>>

    /*
    *  商家详情
    * */
    @GET("store/detail")
    suspend fun getStoreDetail(
        @Query("shop_id") shopId: String
    ): Response<BaseNetModel<ShopInfoBean>>

    /*
    * 商家详情商品列表
    * */
    @GET("prod/by_shop_id")
    suspend fun getStoreGoodsDetail(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
        @Query("keywords") keywords: String,
        @Query("shop_id") shopId: String
    ): Response<BaseNetModel<StoreGoodsBean>>

    /*
    * 钱包明细
    * */
    @GET("user/wallet/detail")
    suspend fun getWalletDetail(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<WalletDetailBean>>

    /*
    * 提现记录
    * */
    @GET("user/wallet/cashdetail")
    suspend fun getCashdetail(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<CashDetailListBean>>

    /*
    * 商学院
    * */
    @GET("comm/college/list")
    suspend fun getCollegeList(): Response<BaseNetModel<CollegeListBean>>

    /*
    * 支付寶
    * */
    @POST("order/pay/ali")
    suspend fun aliPay(@Body zfbPayReq: ZFBPayReq): Response<BaseNetModel<AliPayDataBean>>

    /*
    * 支付宝多订单支付
    * */
    @POST("order/pay/multiple/ali")
    suspend fun orderIdsAliPay(@Body orderIdsReq: OrderIdsReq): Response<BaseNetModel<AliPayDataBean>>


    /*
    * 余额
    * */
    @POST("order/pay/balance")
    suspend fun payBalance(@Body balancePayReq: BalancePayReq): Response<BaseNetModel<Any>>

    /*
    * 余额
    * */
    @POST("order/pay/multiple/balance")
    suspend fun payMultipleBalance(@Body balanceMulPayReq: BalanceMulPayReq): Response<BaseNetModel<Any>>

    /*
    * 修改支付密码
    * */
    @POST("user/setpwd/pay")
    suspend fun setPwdPay(@Body modifyPasReq: ModifyPasReq): Response<BaseNetModel<Any>>

    /*
    * 提现
    * */
    @POST("user/wallet/cashout")
    suspend fun cashout(@Body cashReq: CashReq): Response<BaseNetModel<Any>>

    /*
    * 转积分
    * */
    @POST("user/transfer/point/byphone")
    suspend fun transferPoint(@Body transferPointrReq: TransferPointrReq): Response<BaseNetModel<Any>>

    /*
    * 订单详情
    * */
    @GET("order/detail")
    suspend fun orderDetail(
        @Query("order_id") orderId: String
    ): Response<BaseNetModel<OrderDetailBean>>

    /*
    * 取消订单
    * */
    @POST("order/cancel")
    suspend fun orderCancel(@Body orderIdReq: OrderIdReq): Response<BaseNetModel<Any>>

    /*
    * 确认收货
    * */
    @POST("order/confirm")
    suspend fun orderConfirm(@Body orderIdReq: OrderIdReq): Response<BaseNetModel<Any>>

    /*
    * 删除订单
    * */
    @POST("order/delete")
    suspend fun orderDelete(@Body orderIdReq: OrderIdReq): Response<BaseNetModel<Any>>

    /*
    * 运营中心申请
    * */
    @POST("user/service/apply")
    suspend fun serviceApply(@Body serviceApplyReq: ServiceApplyReq): Response<BaseNetModel<Any>>

    /*
    * 订单评价
    * */
    @POST("order/comment/save")
    suspend fun commentSave(@Body commentReq: CommentReq): Response<BaseNetModel<Any>>

    /*
    * 核销订单列表
    * */
    @GET("order/verify/list")
    suspend fun getVerifyList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<VerifyListBean>>

    /*
    * 分享海报
    * */
    @GET("user/share/info")
    suspend fun getShareInfo(): Response<BaseNetModel<ShareBean>>

    /*
    * 订单核销
    * */
    @POST("order/submit/veri")
    suspend fun submitVeri(@Body submitVeriReq: SubmitVeriReq): Response<BaseNetModel<AliPayDataBean>>

    /*
    * 规格查询
    * */
    @GET("prod/sku/query")
    suspend fun skuQuery(
        @Query("prod_id") prod_id: String,
        @Query("props") props: String
    ): Response<BaseNetModel<SkuInfoBean>>

    /*
    * 是否签到
    * */
    @GET("user/is_signed")
    suspend fun isSign(): Response<BaseNetModel<SignBean>>

    /*
    * 积分信息
    * */
    @GET("user/point/info")
    suspend fun pointInfo(): Response<BaseNetModel<PointInfoBean>>

    /*
    * 单商品计算订单金额
    * */
    @POST("order/calc/direct")
    suspend fun calcDirect(@Body createOrderReq: CreateOrderReq): Response<BaseNetModel<CalcDirectBean>>

    /*
    * 单商品计算订单金额
    * */
    @POST("order/calc/order")
    suspend fun calcOrder(@Body orderIdReq: OrderIdReq): Response<BaseNetModel<CalcDirectBean>>

    /*
    * 多商品计算订单金额
    * */
    @POST("order/calc/multiple/order")
    suspend fun calcMultipleOrder(@Body order_ids: OrderIdsReq): Response<BaseNetModel<CalcDirectBean>>

    /*
    * 获取指定ID的订单列表
    * */
    @GET("order/filter")
    suspend fun orderFilter(
        @Query("order_ids") order_ids: String,
    ): Response<BaseNetModel<OrderFilterBean>>

    /*
    * 获取android更新信息
    * */
    @GET("comm/android/update")
    suspend fun androidUpdate(): Response<BaseNetModel<UpdateBean>>

}