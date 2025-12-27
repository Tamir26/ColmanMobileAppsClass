package com.example.studentsapp.model

data class Student(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var avatarUrl: String = "",
    var isChecked: Boolean = false
)
