package com.app.shop.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.app.shop.base.BaseApplication

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
@Database(entities = [SearchBean::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {
    companion object {
        var dataBase: SearchDatabase

        init {
            //如果databaseBuilder改为inMemoryDatabaseBuilder则创建一个内存数据库（进程销毁后，数据丢失）
            dataBase = Room.databaseBuilder(
                BaseApplication.mContext!!,
                SearchDatabase::class.java,
                "db_user"
            )
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
                //数据库创建和打开后的回调，可以重写其中的方法
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                //数据库升级异常之后的回滚
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun getSearchDao(): SearchDao
    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}