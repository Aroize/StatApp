package ru.ifmo.statapp.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ifmo.statapp.data.db.entity.Group

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface GroupCreatorView : MvpView {
    fun showGroups(groups: List<Group>)

    fun showErrorMessage(messageRes: Int)

    fun showErrorMessage(message: String?)

    fun addGroup(group: Group)
}