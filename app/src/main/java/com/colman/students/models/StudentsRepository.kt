package com.colman.students.models

class StudentsRepository private constructor() {

    private val students = mutableListOf<Student>()

    fun getAllStudents(): List<Student> {
        return students.toList()
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student): Boolean {
        if (students.any { it.id == student.id }) {
            return false
        }
        students.add(student)
        return true
    }

    fun updateStudent(oldId: String, updatedStudent: Student): Boolean {
        val index = students.indexOfFirst { it.id == oldId }

        if (index == -1) return false

        if (oldId != updatedStudent.id && students.any { it.id == updatedStudent.id }) {
            return false
        }

        students[index] = updatedStudent

        return true
    }

    fun deleteStudent(id: String): Boolean {
        return students.removeIf { it.id == id }
    }

    fun toggleCheckStatus(id: String) {
        val index = students.indexOfFirst { it.id == id }

        if (index != -1) {
            val student = students[index]
            students[index] = student.copy(isChecked = !student.isChecked)
        }
    }

    companion object {
        val shared = StudentsRepository()
    }
}
