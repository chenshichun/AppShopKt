package com.app.shop.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.app.shop.BuildConfig
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UpdateBean
import com.app.shop.databinding.ActivitySettingBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.SettingContract
import com.app.shop.ui.presenter.SettingPresenter
import com.app.shop.util.AppUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.dialog.UpdateApkDialog
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import okhttp3.Call
import java.io.File

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：设置
 */
class SettingActivity : BaseActivity<ActivitySettingBinding, SettingPresenter>(),
    SettingContract.View,
    View.OnClickListener {
    override fun getPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "设置"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.stv1.setOnClickListener(this)
        binding.stv2.setOnClickListener(this)
        binding.stv3.setOnClickListener(this)
        binding.stv4.setOnClickListener(this)
    }


    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.stv_1 -> {
                mPresenter!!.androidUpdate()
            }
            R.id.stv_2 -> {
                startActivity(Intent(this, AccountSecurityActivity::class.java))
            }
            R.id.stv_3 -> {
                startActivity(Intent(this, AboutUsActivity::class.java))
            }
            R.id.stv_4 -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("您确定要退出登录吗？")
                builder.setTitle("提示")
                builder.setPositiveButton("确定") { _, _ ->
                    AccountManager.signOut()
                    startActivity(Intent(this, AccountLoginActivity::class.java))
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }
        }
    }



    lateinit var updateBean: UpdateBean
    override fun androidUpdate(mData: BaseNetModel<UpdateBean>) {
        updateBean = mData.data!!
        if (mData.data!!.apk_info.version_code.toInt() > AppUtil.getVersionCode(this)!!.toInt()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {// 更新
                showUpdateDialog(mData.data!!);
            }
        }else{
            ToastUtil.showToast("当前是最新版本")
        }
    }

    private fun showUpdateDialog(updateBean: UpdateBean) {
        val updateApkDialog =
            UpdateApkDialog(this@SettingActivity, R.style.CustomDialog)
        updateApkDialog.setOnClickListener(object : UpdateApkDialog.OnClickListener {
            override fun update() {
                downloadApp(updateBean)
                ToastUtil.showToast("后台下载中")
                updateApkDialog.dismiss()
            }
        })
        updateApkDialog.show()
    }

    private fun downloadApp(updateBean: UpdateBean) {
        OkHttpUtils.get()
            .url(updateBean.apk_info.download_url)
            .build()
            .execute(object : FileCallBack(
                /*Environment.getExternalStorageDirectory().absolutePath*/"data/data/com.app.shop/files/",
                "yhj.apk"
            ) {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    ToastUtil.showToast("下载失败")
                    Logger.d(e!!.message)
                }

                override fun onResponse(response: File?, id: Int) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        val contentUri = FileProvider.getUriForFile(
                            this@SettingActivity, BuildConfig.APPLICATION_ID + ".provider",
                            response!!
                        )
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
                    } else {
                        intent.setDataAndType(
                            Uri.fromFile(response),
                            "application/vnd.android.package-archive"
                        )
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }
            })
    }


}