package com.colman.students.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            tvId.text = getString(R.string.student_id_label, student.id)
            tvPhone.text = getString(R.string.student_phone_label, student.phone)
            tvAddress.text = getString(R.string.student_address_label, student.address)
            cbIsChecked.isChecked = student.isChecked
            ivStudentAvatar.setImageResource(R.drawable.user)
        }
    }

    private fun setupListeners() {
        binding.btnEdit.setOnClickListener {
            val editIntent = Intent(this, EditStudentActivity::class.java)
            editIntent.putExtra(Constants.STUDENT_ID_KEY, studentId)
            startActivity(editIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
