package com.colman.students.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.colman.students.R
import com.colman.students.base.Constants
import com.colman.students.databinding.ActivityEditStudentBinding
import com.colman.students.models.Student
import com.colman.students.models.StudentsRepository

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var originalId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalId = intent.getStringExtra(Constants.STUDENT_ID_KEY)

        if (originalId == null) {
            finish()
            return
        }

        loadStudentData()
        setupListeners()
    }

    private fun loadStudentData() {
        val id = originalId ?: return
        val student = StudentsRepository.shared.getStudentById(id)
        if (student != null) {
            with(binding) {
                editStudentName.setText(student.name)
                editStudentId.setText(student.id)
                editStudentPhone.setText(student.phone)
                editStudentAddress.setText(student.address)
                editStudentCheck.isChecked = student.isChecked
            }
        } else {
            finish()
        }
    }

    private fun setupListeners() {
        binding.editStudentCancelBtn.setOnClickListener {
            finish()
        }

        binding.editStudentSaveBtn.setOnClickListener {
            saveStudent()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun saveStudent() {
        val name = binding.editStudentName.text.toString()
        val id = binding.editStudentId.text.toString()
        val phone = binding.editStudentPhone.text.toString()
        val address = binding.editStudentAddress.text.toString()
        val isChecked = binding.editStudentCheck.isChecked

        if (name.isBlank() || id.isBlank() || phone.isBlank() || address.isBlank()) {
            Toast.makeText(this, R.string.error_missing_fields, Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStudent = Student(
            id = id,
            name = name,
            phone = phone,
            address = address,
            isChecked = isChecked
        )

        val currentId = originalId ?: return
        val success = StudentsRepository.shared.updateStudent(currentId, updatedStudent)

        if (success) {
            finish()
        } else {
            Toast.makeText(this, R.string.error_update_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_confirmation_title)
            .setMessage(R.string.delete_confirmation_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteStudent()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun deleteStudent() {
        val id = originalId ?: return
        val success = StudentsRepository.shared.deleteStudent(id)
        if (success) {
            finish()
        }
    }
}
