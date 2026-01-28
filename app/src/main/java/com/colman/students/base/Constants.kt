package com.colman.students.base

import com.colman.students.models.Student

typealias StudentsCompletion = (List<Student>) -> Unit
typealias StudentCompletion = (Student) -> Unit
typealias Completion = () -> Unit

object Constants {
    const val STUDENT_ID_KEY = "student_id"
}
