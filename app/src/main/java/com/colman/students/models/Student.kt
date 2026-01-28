package com.colman.students.models

data class Student(
    val id: String,
    val name: String,
    val phone: String,
    val address: String,
    var isChecked: Boolean = false
) {
    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val PHONE_KEY = "phone"
        const val ADDRESS_KEY = "address"
        const val IS_CHECKED_KEY = "isChecked"
    }
}
