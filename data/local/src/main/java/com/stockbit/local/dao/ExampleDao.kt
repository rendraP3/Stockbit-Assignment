package com.stockbit.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.stockbit.model.ExampleModel
import java.util.*

@Dao
abstract class ExampleDao: BaseDao<ExampleModel>() {

    @Query("SELECT * FROM ExampleModel WHERE name = :name LIMIT 1")
    abstract fun getExample(name: String): ExampleModel

    fun save(data: ExampleModel) {
        insert(data)
    }

    fun save(datas: List<ExampleModel>) {
        insert(datas)
    }
}