package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import ru.ifmo.statapp.R

class StudentListFragment : MvpAppCompatFragment() {

    private lateinit var studentList: RecyclerView
    private lateinit var addStudentBtn: Button

    private lateinit var mainView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_student_group_list, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        studentList = mainView.findViewById(R.id.student_list)
        addStudentBtn = mainView.findViewById(R.id.add_student_btn)

    }

    companion object {
        const val groupIdKey = "group.id"
    }
}