package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model
import com.squareup.picasso.Picasso

class StudentDetailsActivity : AppCompatActivity() {

    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Student Details"

        studentId = intent.getStringExtra("student_id")

        val editButton: Button = findViewById(R.id.student_details_edit_button)

        // Initial UI update happens in onResume to handle updates from Edit screen
        
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_id", studentId)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun updateUI() {
         val student = Model.shared.students.find { it.id == studentId }
        
        if (student == null) {
            finish()
            return
        }

        val nameTextView: TextView = findViewById(R.id.student_details_name_text_view)
        val idTextView: TextView = findViewById(R.id.student_details_id_text_view)
        val phoneTextView: TextView = findViewById(R.id.student_details_phone_text_view)
        val addressTextView: TextView = findViewById(R.id.student_details_address_text_view)
        val checkedCheckBox: CheckBox = findViewById(R.id.student_details_checked_check_box)
        val imageView: ImageView = findViewById(R.id.student_details_image_view)

        nameTextView.text = "Name: ${student.name}"
        idTextView.text = "ID: ${student.id}"
        phoneTextView.text = "Phone: ${student.phone}"
        addressTextView.text = "Address: ${student.address}"
        checkedCheckBox.isChecked = student.isChecked
        
        if (student.avatarUrl.isNotEmpty()) {
            Picasso.get()
                .load(student.avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
        } else {
             imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}
