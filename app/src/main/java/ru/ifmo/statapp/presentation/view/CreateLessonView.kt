package ru.ifmo.statapp.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CreateLessonView : MvpView {
    fun showError(message: String?)
    fun successfulInsert()
}