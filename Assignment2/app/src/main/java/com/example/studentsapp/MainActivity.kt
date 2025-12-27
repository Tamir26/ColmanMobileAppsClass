package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.adapter.StudentsAdapter
import com.example.studentsapp.model.Model
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var studentsRecyclerView: RecyclerView? = null
    private var adapter: StudentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentsRecyclerView = findViewById(R.id.students_recycler_view)
        studentsRecyclerView?.setHasFixedSize(true)
        studentsRecyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = StudentsAdapter(Model.shared.students)
        adapter?.listener = object : StudentsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val student = Model.shared.students[position]
                val intent = Intent(this@MainActivity, StudentDetailsActivity::class.java)
                intent.putExtra("student_id", student.id)
                startActivity(intent)
            }
        }
        studentsRecyclerView?.adapter = adapter

        val addStudentFab: FloatingActionButton = findViewById(R.id.add_student_fab)
        addStudentFab.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}
