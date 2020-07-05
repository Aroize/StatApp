package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.ifmo.statapp.R
import ru.ifmo.statapp.domain.MainState
import ru.ifmo.statapp.domain.MainStateAcceptor
import kotlin.IllegalArgumentException

class PickerFragment : Fragment(), View.OnClickListener {

    private lateinit var mainView: View
    private lateinit var listLessonsBtn: Button
    private lateinit var createGroupBtn: Button
    private lateinit var statisticsBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_picker, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        listLessonsBtn = mainView.findViewById(R.id.lessons_btn)
        createGroupBtn = mainView.findViewById(R.id.create_group_btn)
        statisticsBtn = mainView.findViewById(R.id.statistics_btn)
        listLessonsBtn.setOnClickListener(this)
        createGroupBtn.setOnClickListener(this)
        statisticsBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v ?: return
        val state = when (v.id) {
            R.id.lessons_btn -> MainState.LIST_LESSONS
            R.id.create_group_btn -> MainState.CREATE_GROUP
            R.id.statistics_btn -> MainState.STATISTICS
            else -> throw IllegalArgumentException("Stub")
        }
        (activity as MainStateAcceptor).acceptState(state)
    }
}