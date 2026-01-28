package com.colman.students.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.colman.students.databinding.StudentListRowBinding
import com.colman.students.models.Student
import com.colman.students.models.StudentsRepository

class StudentAdapter(
    private val students: List<Student>,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(val binding: StudentListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentListRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.itemView.setOnClickListener {
            onItemClick(student)
        }

        with(holder.binding) {
            studentName.text = student.name
            studentId.text = student.id

            studentCheckbox.setOnCheckedChangeListener(null)
            studentCheckbox.isChecked = student.isChecked

            studentCheckbox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
                StudentsRepository.shared.toggleCheckStatus(student.id)
            }
        }
    }
}
