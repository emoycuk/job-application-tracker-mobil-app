package com.emirkilic.hw2

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirkilic.hw2.adapters.CustomRecyclerViewAdapter
import com.emirkilic.hw2.databinding.ActivityMainBinding
import com.emirkilic.hw2.databinding.DialogAddJobBinding
import com.emirkilic.hw2.db.JobAppDAO
import com.emirkilic.hw2.db.JobApplicationDatabase
import com.emirkilic.hw2.db.Job_Application

class MainActivity : AppCompatActivity(),CustomRecyclerViewAdapter.RecyclerAdapterInterface {

    lateinit  var bindingMain: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var joblist: ArrayList<Job_Application>
    lateinit var adapter: CustomRecyclerViewAdapter
    lateinit var dao: JobAppDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val db = JobApplicationDatabase.getDatabase(this)
        dao = db.jobAppDAO()

        layoutManager = LinearLayoutManager(this)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        bindingMain.jobRecyclerView.layoutManager = layoutManager

        dao.deleteAll()

        dao.insertJobApp(
            Job_Application(

                company = "Amazon",
                title = "Cloud Engineer Intern",
                status = "Interview",
                priority = "NORMAL"
            )
        )

        joblist = ArrayList(dao.getAll())
        adapter = CustomRecyclerViewAdapter(this, joblist)
        bindingMain.jobRecyclerView.setAdapter(adapter)

        bindingMain.btnAddJob.setOnClickListener {
            showAddJobDialog()
        }


    }

    override fun maintainJob(sc: Job_Application) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("job_id", sc.id)

        startActivity(intent)
    }

    fun showAddJobDialog(){

        lateinit var bindingRelatedWithDialog: DialogAddJobBinding
        var customDialog = Dialog(this)
        bindingRelatedWithDialog = DialogAddJobBinding.inflate(layoutInflater)
        customDialog.setContentView(bindingRelatedWithDialog.root)

        bindingRelatedWithDialog.btnSave.setOnClickListener setOnClickListener@{

            val company = bindingRelatedWithDialog.etCompany.text.toString().trim()
            val title = bindingRelatedWithDialog.etTitle.text.toString().trim()
            val status = "Applied"
            val priority = bindingRelatedWithDialog.etPriority.text.toString().trim()

            // EMPTY CHECK
            if (company.isEmpty()) {
                Toast.makeText(this, "Company cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // PRIORITY CHECK
            val validPriorities = listOf("HIGH", "NORMAL")

            val finalPriority =
                if (priority.isEmpty()) "NORMAL"
                else priority.uppercase()

            if (finalPriority !in validPriorities) {
                Toast.makeText(this, "Priority must be HIGH or NORMAL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // INSERT RECORD
            val newJob = Job_Application(
                id = 0,
                company = company,
                title = title,
                status = status,
                priority = finalPriority
            )

            dao.insertJobApp(newJob)

            joblist.clear()
            joblist.addAll(dao.getAll())
            adapter.notifyDataSetChanged()

            customDialog.dismiss()
            //customDialog.hide();
            //if it is too expensive to create it, call hide() otherwise call dismiss()
            //if you hide and and call the finish() method for the activity, this may cause problem.
            //if you hide a dialog before finish() method call dismiss() method for the dialog
        }
        bindingRelatedWithDialog.btnCancel.setOnClickListener(View.OnClickListener { //customDialog.hide();
            customDialog.dismiss()
        })

        customDialog.show()
    }

    override fun onResume() {
        super.onResume()
        joblist.clear()
        joblist.addAll(dao.getAll())
        adapter.notifyDataSetChanged()
    }
}