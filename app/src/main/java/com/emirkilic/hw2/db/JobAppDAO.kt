package com.emirkilic.hw2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emirkilic.hw2.db.Job_Application
import com.emirkilic.hw2.util.Constants


@Dao
interface JobAppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJobApp(app: Job_Application): Long

    @Update
    fun updateJobApp(app: Job_Application): Int

    @Delete
    fun deleteJobApp(app: Job_Application): Int

    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY id DESC")
    fun getAll(): MutableList<Job_Application>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id = :id")
    fun getById(id: Int): Job_Application

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE company LIKE :name")
    fun getByCompanyName(name: String): MutableList<Job_Application>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(apps: List<Job_Application>) {
        apps.forEach {
            insertJobApp(it)
        }
    }
}