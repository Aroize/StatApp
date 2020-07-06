package ru.ifmo.statapp.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ifmo.statapp.data.db.entity.Student

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface StudentListView : MvpView {

    fun showError(message: String?)

    fun showStudents(studentList: List<Student>)

    fun addStudent(student: Student)
}