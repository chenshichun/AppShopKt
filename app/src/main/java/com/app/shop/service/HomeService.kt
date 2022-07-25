package com.app.shop.service

import com.app.shop.bean.*
import com.app.shop.req.CertReq
import com.app.shop.req.MerchantSettledReq
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
    @GET("prod/home")
    suspend fun getProdHomeData(): Response<BaseNetModel<ProdBean>>

    /*
    * 精选商品
    * */
    @GET("prod/featured")
    suspend fun getProdFeaturedData(): Response<BaseNetModel<ProdBean>>

    /*
    * 推荐商品
    * */
    @GET("prod/recommend")
    suspend fun getProdRecommendData(): Response<BaseNetModel<ProdBean>>

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
    suspend fun listPoint(): Response<BaseNetModel<PointBean>>

    /*
    * 实名认证
    * */
    @POST("user/cert")
    suspend fun cert(@Body certReq: CertReq): Response<BaseNetModel<Any>>
}