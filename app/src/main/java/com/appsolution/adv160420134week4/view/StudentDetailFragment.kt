package com.appsolution.adv160420134week4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appsolution.adv160420134week4.R
import com.appsolution.adv160420134week4.model.Student
import com.appsolution.adv160420134week4.viewmodel.DetailViewModel
import com.appsolution.adv160420134week4.viewmodel.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()
        observeDetailViewModel()
    }
    fun observeDetailViewModel(){
        val studentID = view?.findViewById<TextInputEditText>(R.id.txtStudID)
        val studentName = view?.findViewById<TextInputEditText>(R.id.txtStudName)
        val studentBoD = view?.findViewById<TextInputEditText>(R.id.txtStudBoD)
        val studentPhone = view?.findViewById<TextInputEditText>(R.id.txtStudPhone)
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {studentID?.setText(it.id);studentName?.setText(it.name);
            studentBoD?.setText(it.dob);studentPhone?.setText(it.phone)})
    }
}