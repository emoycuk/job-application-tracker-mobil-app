package com.emirkilic.hw2.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emirkilic.hw2.util.Constants

@Entity(tableName = Constants.TABLENAME)
class Job_Application(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var company:String,
    var title:String,
    var status: String,
    var priority: String

) {

    override fun toString(): String {
        return "Job{" +
                "id=" + id +
                ", Company='" + company + '\'' +
                ", Job Title='" + title + '\'' +
                ", Status='" + status + '\'' +
                ", Priority=" + priority +
                '}'
    }
}