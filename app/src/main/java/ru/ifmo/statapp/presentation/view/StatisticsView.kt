package ru.ifmo.statapp.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ifmo.statapp.data.db.entity.Student

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface StatisticsView : MvpView {
    fun showStudents(students: List<Student>)

    fun showStatistics(count: Int)
}