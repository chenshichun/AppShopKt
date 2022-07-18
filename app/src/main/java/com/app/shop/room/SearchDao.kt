package com.app.shop.room

import androidx.room.*

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putSearchBean(searchBean: SearchBean)

    @Query("select * from Search")
    fun getAllSearch(): List<SearchBean>?

    @Delete
    fun delete(searchBean: SearchBean)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(searchBean: SearchBean)
}