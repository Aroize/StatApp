package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.domain.iteractor.GroupsIteractor
import ru.ifmo.statapp.domain.iteractor.StudentsIteractor
import ru.ifmo.statapp.domain.iteractor.StudentsIteractorImpl
import ru.ifmo.statapp.presentation.presenter.GroupsCreatorPresenter
import ru.ifmo.statapp.presentation.presenter.StudentListPresenter
import javax.inject.Singleton

@Module
object PresenterModule {

    @Singleton
    @Provides
    fun provideGroupsPresenter(groupsIteractor: GroupsIteractor) = GroupsCreatorPresenter(groupsIteractor)

    @Provides
    fun provideStudentListPresenter(studentIteractor: StudentsIteractor) = StudentListPresenter(studentIteractor)
}