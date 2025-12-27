package com.example.studentsapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "New Student"

        val nameEditText: EditText = findViewById(R.id.new_student_name_edit_text)
        val idEditText: EditText = findViewById(R.id.new_student_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.new_student_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.new_student_address_edit_text)
        val checkedCheckBox: CheckBox = findViewById(R.id.new_student_checked_check_box)
        val saveButton: Button = findViewById(R.id.new_student_save_button)
        val cancelButton: Button = findViewById(R.id.new_student_cancel_button)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()
            val isChecked = checkedCheckBox.isChecked
            
            val avatarUrl = "https://robohash.org/${id.ifEmpty { name }}?set=set5"

            val newStudent = Student(id, name, phone, address, avatarUrl, isChecked)
            Model.shared.students.add(newStudent)
            finish()
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
