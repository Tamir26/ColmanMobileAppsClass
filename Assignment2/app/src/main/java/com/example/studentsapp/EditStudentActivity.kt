package com.example.studentsapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student
import com.squareup.picasso.Picasso

class EditStudentActivity : AppCompatActivity() {

    private var studentId: String? = null
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Student"

        studentId = intent.getStringExtra("student_id")
        student = Model.shared.students.find { it.id == studentId }

        val nameEditText: EditText = findViewById(R.id.edit_student_name_edit_text)
        val idEditText: EditText = findViewById(R.id.edit_student_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.edit_student_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.edit_student_address_edit_text)
        val checkedCheckBox: CheckBox = findViewById(R.id.edit_student_checked_check_box)
        val saveButton: Button = findViewById(R.id.edit_student_save_button)
        val cancelButton: Button = findViewById(R.id.edit_student_cancel_button)
        val deleteButton: Button = findViewById(R.id.edit_student_delete_button)
        val imageView: ImageView = findViewById(R.id.edit_student_image_view)

        student?.let {
            nameEditText.setText(it.name)
            idEditText.setText(it.id)
            phoneEditText.setText(it.phone)
            addressEditText.setText(it.address)
            checkedCheckBox.isChecked = it.isChecked
            
            if (it.avatarUrl.isNotEmpty()) {
                Picasso.get()
                    .load(it.avatarUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }

        deleteButton.setOnClickListener {
            student?.let {
                Model.shared.students.remove(it)
            }
            finish()
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()
            val isChecked = checkedCheckBox.isChecked

            student?.let {
                it.name = name
                it.id = id
                it.phone = phone
                it.address = address
                it.isChecked = isChecked
            }
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
