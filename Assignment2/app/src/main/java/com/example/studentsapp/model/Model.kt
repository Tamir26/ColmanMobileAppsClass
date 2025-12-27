package com.example.studentsapp.model

class Model private constructor() {

    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    init {
        // Initialize with some dummy data for testing
        for (i in 0..10) {
            val student = Student(
                name = "Student Name $i",
                id = "$i",
                phone = "054-123456$i",
                address = "Some Street $i",
                avatarUrl = "https://robohash.org/$i?set=set5", // Random human avatar
                isChecked = false
            )
            students.add(student)
        }
    }
}
