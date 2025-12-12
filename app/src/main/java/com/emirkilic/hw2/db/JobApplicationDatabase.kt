package com.emirkilic.hw2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emirkilic.hw2.util.Constants
import kotlinx.coroutines.Job


@Database(entities = [Job_Application::class], version = 1)
abstract class JobApplicationDatabase : RoomDatabase() {

    abstract fun jobAppDAO(): JobAppDAO

    companion object{
        @Volatile  //it makes that instance to visible to other threads
        private var INSTANCE: JobApplicationDatabase?=null

        fun getDatabase(context: Context): JobApplicationDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }
            /*
            everthing in this block protected from concurrent execution by multiple threads.In this block database instance is created
            same database instance will be used. If many instance are used, it will be so expensive
             */
            synchronized(this){
                val  instance = Room.databaseBuilder(context.applicationContext,
                    JobApplicationDatabase::class.java, Constants.DATABASENAME).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

    }
}