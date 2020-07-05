package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.ifmo.statapp.R

class GroupCreatorFragment : Fragment() {

    private lateinit var createGroupBtn: Button
    private lateinit var groupList: RecyclerView

    private lateinit var mainView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_group_creator, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        createGroupBtn = mainView.findViewById(R.id.create_group_btn)
        groupList = mainView.findViewById(R.id.group_recycler)
        //TODO(Inflate with correct views)
    }
}