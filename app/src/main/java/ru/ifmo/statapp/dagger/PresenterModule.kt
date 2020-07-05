package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.domain.iteractor.GroupsIteractor
import ru.ifmo.statapp.presentation.presenter.GroupsCreatorPresenter
import javax.inject.Singleton

@Module
object PresenterModule {

    @Singleton
    @Provides
    fun provideGroupsPresenter(groupsIteractor: GroupsIteractor) = GroupsCreatorPresenter(groupsIteractor)

}