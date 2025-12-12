package com.emirkilic.hw2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.emirkilic.hw2.databinding.ActivityDetailBinding
import com.emirkilic.hw2.databinding.ActivityMainBinding
import com.emirkilic.hw2.db.JobAppDAO
import com.emirkilic.hw2.db.JobApplicationDatabase
import com.emirkilic.hw2.db.Job_Application

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var dao: JobAppDAO
    lateinit var curr_job: Job_Application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("job_id",-1).toInt()


            val db = JobApplicationDatabase.getDatabase(this)
            dao = db.jobAppDAO()

            curr_job = dao.getById(id)

            binding.tvCompanyValue.text = "Company: ${curr_job.company}"
            binding.tvTitleValue.text = "Title: ${curr_job.title}"
            binding.tvStatusValue.text = "Status: ${curr_job.status}"

            binding.priorityToggleView.setPriority(curr_job.priority)
            binding.priorityToggleView.setOnPriorityChangedListener { newPriority ->
                curr_job.priority = newPriority

            }



        binding.btnUpdate.setOnClickListener {

            val new_status = binding.etStatus.text.toString().trim()
            val new_title = binding.etTitleDetail.text.toString().trim()

            if (new_title.isNotEmpty() ) {
                curr_job.title = new_title
            }

            if (new_status.isNotEmpty()) {

                val validStatus = listOf("APPLIED", "INTERVIEW", "OFFER", "REJECTED")

                val temp_status = new_status.uppercase()

                when (temp_status) {
                    "APPLIED" -> curr_job.status = "Applied"
                    "INTERVIEW" -> curr_job.status = "Interview"
                    "OFFER" -> curr_job.status = "Offer"
                    "REJECTED" -> curr_job.status = "Rejected"
                    else -> {
                        Toast.makeText(
                            this,
                            "Status must be Applied, Interview, Offer or Rejected",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                }

            }


            dao.updateJobApp(curr_job)

            finish()



        }

        binding.btnDelete.setOnClickListener {

            dao.deleteJobApp(curr_job)

            finish()

        }
    }
}