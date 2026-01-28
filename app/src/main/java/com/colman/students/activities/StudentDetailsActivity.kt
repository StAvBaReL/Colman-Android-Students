package com.colman.students.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.colman.students.R
import com.colman.students.base.Constants
import com.colman.students.databinding.ActivityStudentDetailsBinding
import com.colman.students.models.StudentsRepository

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra(Constants.STUDENT_ID_KEY)

        if (studentId == null) {
            finish()
            return
        }

        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadStudentDetails()
    }

    private fun loadStudentDetails() {
        val student = StudentsRepository.shared.getStudentById(studentId!!)
        if (student == null) {
            finish()
            return
        }

        binding.apply {
            tvName.text = student.name
            tvId.text = "ID: ${student.id}"
            tvPhone.text = "Phone: ${student.phone}"
            tvAddress.text = "Address: ${student.address}"
            cbIsChecked.isChecked = student.isChecked
            ivStudentAvatar.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    private fun setupListeners() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(Constants.STUDENT_ID_KEY, studentId)
            startActivity(intent)
        }
    }
}
