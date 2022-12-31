package com.app.shop.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.MultipleImagesAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.databinding.ActivityComplaintBinding
import com.app.shop.ui.contract.ComplaintContract
import com.app.shop.ui.presenter.ComplaintPresenter
import com.app.shop.util.CommonUtil
import com.app.shop.util.IntentUtil
import com.app.shop.view.GlideEngine
import com.gyf.immersionbar.ktx.immersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：投诉
 */
class ComplaintActivity : BaseActivity<ActivityComplaintBinding, ComplaintPresenter>(),
    ComplaintContract.View {

    private lateinit var multipleImagesAdapter: MultipleImagesAdapter
    private var imgList = mutableListOf<String>()

    override fun getPresenter(): ComplaintPresenter {
        return ComplaintPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "投诉"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        val width: Int = (CommonUtil.getScreenWidthPixels(this) - CommonUtil.dip2px(this, 60f)) / 3
        multipleImagesAdapter = MultipleImagesAdapter(this, imgList, width)
        multipleImagesAdapter.setMaxImages(3)
        multipleImagesAdapter.setAddImg(R.drawable.icon_add_pic)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.mRecyclerView.adapter = multipleImagesAdapter
        multipleImagesAdapter.setOnItemClickListener(object :
            MultipleImagesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putStringArrayList("paths", imgList as ArrayList<String>?)
                bundle.putInt("index", position)
                IntentUtil.get()!!.goActivity(this@ComplaintActivity, ShowListImageActivity::class.java, bundle)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDeleteClick(position: Int) {
                imgList.removeAt(position)
                multipleImagesAdapter.notifyDataSetChanged()
            }

            override fun onAddClick(position: Int) {
                chooseImg()
            }
        })
    }

    /*
    * 上传图片
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun upload(mData: BaseNetModel<UploadBean>) {
        imgList.add(mData.data!!.url)
        multipleImagesAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    @SuppressLint("CheckResult")
    fun chooseImg() {
        //获取写的权限
        val rxPermission = RxPermissions(this)
        rxPermission.requestEach(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
            .subscribe { permission ->
                if (permission.granted) { // 用户已经同意该权限
                    PictureSelector.create(this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setMaxSelectNum(3 - imgList.size)
                        .forResult(object : OnResultCallbackListener<LocalMedia?> {
                            override fun onResult(result: ArrayList<LocalMedia?>?) {
                                for (localMedia in result!!) {
                                    val file = File(localMedia!!.realPath)
                                    val requestBody = RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        file
                                    )
                                    val fromData = MultipartBody.Part.createFormData(
                                        "file",
                                        file.name,
                                        requestBody
                                    )

                                    val description: RequestBody = RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        "merchant"
                                    )
                                    mPresenter!!.upload(fromData, description)
                                }
                            }

                            override fun onCancel() {

                            }
                        })
                } else {
                    Toast.makeText(
                        this,
                        "拒绝",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}