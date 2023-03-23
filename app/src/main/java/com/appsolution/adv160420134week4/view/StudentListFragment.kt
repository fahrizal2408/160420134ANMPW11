package com.appsolution.adv160420134week4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsolution.adv160420134week4.R
import com.appsolution.adv160420134week4.viewmodel.ListViewModel

class StudentListFragment : Fragment() {
    private lateinit var viewModel:ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
        val recView = view?.findViewById<RecyclerView>(R.id.recView)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        recView?.layoutManager = LinearLayoutManager(context)
        recView?.adapter = studentListAdapter
        observeViewModel()
    }
    fun observeViewModel() {
        val recView = view?.findViewById<RecyclerView>(R.id.recView)
        val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
        val txtError = view?.findViewById<TextView>(R.id.textError)

        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })
        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                txtError?.visibility = View.VISIBLE
            } else {
                txtError?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                recView?.visibility = View.GONE
                progressLoad?.visibility = View.VISIBLE
            } else {
                recView?.visibility = View.VISIBLE
                progressLoad?.visibility = View.GONE
            }
        })
    }
}