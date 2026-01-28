package com.colman.students.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.colman.students.adapters.StudentAdapter
import com.colman.students.base.Constants
import com.colman.students.databinding.ActivityStudentListBinding
import com.colman.students.models.StudentsRepository

class StudentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.studentsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addStudentFab.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val students = StudentsRepository.shared.getAllStudents()
        binding.studentsRecyclerView.adapter = StudentAdapter(students) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra(Constants.STUDENT_ID_KEY, student.id)
            startActivity(intent)
        }
    }
}