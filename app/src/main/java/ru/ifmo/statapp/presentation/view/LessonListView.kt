package ru.ifmo.statapp.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ifmo.statapp.data.db.entity.Lesson

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface LessonListView : MvpView {
    fun showLessons(lessons: List<Lesson>)
}