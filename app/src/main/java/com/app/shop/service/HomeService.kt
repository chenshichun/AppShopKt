package com.app.shop.service

import com.app.shop.bean.*
import com.app.shop.req.CartReq
import com.app.shop.req.CertReq
import com.app.shop.req.MerchantSettledReq
import com.app.shop.req.WalletSetReq
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
    * 购物车
    * */
    @GET("user/cart/list")
    suspend fun getCartData(
    ): Response<BaseNetModel<CartBean>>

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
    @GET("/user/team/all")
    suspend fun teamAll(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseNetModel<TeamAllBean>>

}