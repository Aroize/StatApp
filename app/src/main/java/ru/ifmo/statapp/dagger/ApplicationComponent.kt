package ru.ifmo.statapp.dagger

import dagger.Component
import ru.ifmo.statapp.presentation.activity.LoginActivity
import ru.ifmo.statapp.presentation.fragment.CreateLessonFragment
import ru.ifmo.statapp.presentation.fragment.GroupCreatorFragment
import ru.ifmo.statapp.presentation.fragment.LessonsListFragment
import ru.ifmo.statapp.presentation.fragment.StudentListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [IteractorModule::class, DatabaseModule::class, PresenterModule::class])
interface ApplicationComponent {
    fun inject(activity: LoginActivity)

    fun inject(fragment: GroupCreatorFragment)

    fun inject(fragment: StudentListFragment)

    fun inject(fragment: LessonsListFragment)

    fun inject(fragment: CreateLessonFragment)
}