package com.colman.students.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.colman.students.R
import com.colman.students.databinding.ActivityAddStudentBinding
import com.colman.students.models.Student
import com.colman.students.models.StudentsRepository

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupListeners()
    }

    private fun setupListeners() {
        binding.addStudentSaveButton.setOnClickListener {
            saveStudent()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveStudent() {
        val name = binding.addStudentNameEditText.text.toString().trim()
        val id = binding.addStudentIdEditText.text.toString().trim()
        val phone = binding.addStudentPhoneEditText.text.toString().trim()
        val address = binding.addStudentAddressEditText.text.toString().trim()
        val isChecked = binding.addStudentCheckBox.isChecked

        if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, R.string.error_missing_fields, Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            id = id,
            name = name,
            phone = phone,
            address = address,
            isChecked = isChecked
        )

        val success = StudentsRepository.shared.addStudent(student)

        if (success) {
            finish()
        } else {
            Toast.makeText(this, R.string.error_student_exists, Toast.LENGTH_SHORT).show()
        }
    }
}
