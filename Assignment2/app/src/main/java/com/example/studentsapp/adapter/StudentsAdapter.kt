package com.example.studentsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.model.Student
import com.squareup.picasso.Picasso

class StudentsAdapter(private val students: List<Student>) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class StudentViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.student_row_name_text_view)
        val idTextView: TextView = itemView.findViewById(R.id.student_row_id_text_view)
        val checkBox: CheckBox = itemView.findViewById(R.id.student_row_check_box)
        val imageView: ImageView = itemView.findViewById(R.id.student_row_image)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_list_row, parent, false)
        return StudentViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.name
        holder.idTextView.text = student.id
        holder.checkBox.isChecked = student.isChecked

        // Load image using Picasso
        if (student.avatarUrl.isNotEmpty()) {
            Picasso.get()
                .load(student.avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView)
        } else {
             holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.checkBox.setOnClickListener {
            student.isChecked = holder.checkBox.isChecked
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}
