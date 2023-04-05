package com.appsolution.adv160420134week4.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.appsolution.adv160420134week4.R
import com.appsolution.adv160420134week4.model.Student
import com.appsolution.adv160420134week4.util.loadImage

class StudentListAdapter(val studenList:ArrayList<Student>)
    :RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>()
{
    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studenList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val txtID= holder.view.findViewById<TextView>(R.id.txtID)
        val txtName= holder.view.findViewById<TextView>(R.id.txtName)
        val btnDetail= holder.view.findViewById<Button>(R.id.btnDetail)

        txtID.text = studenList[position].id
        txtName.text = studenList[position].name
        btnDetail.setOnClickListener {
            val studentid = txtID.text.toString()
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentDetailFragment(studentid)
            Navigation.findNavController(it).navigate(action)
        }
        var imageView = holder.view.findViewById<ImageView>(R.id.imageView)
        var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBar)
        imageView.loadImage(studenList[position].photoUrl, progressBar)
    }
    fun updateStudentList(newStudentList: ArrayList<Student>) {
        studenList.clear()
        studenList.addAll(newStudentList)
        notifyDataSetChanged()
    }
}