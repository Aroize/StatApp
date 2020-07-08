package ru.ifmo.statapp.dagger

import dagger.Component
import ru.ifmo.statapp.presentation.activity.LoginActivity
import ru.ifmo.statapp.presentation.fragment.*
import javax.inject.Singleton

@Singleton
@Component(modules = [IteractorModule::class, DatabaseModule::class, PresenterModule::class])
interface ApplicationComponent {
    fun inject(activity: LoginActivity)

    fun inject(fragment: GroupCreatorFragment)

    fun inject(fragment: StudentListFragment)

    fun inject(fragment: LessonsListFragment)

    fun inject(fragment: CreateLessonFragment)

    fun inject(fragment: LessonInfoFragment)

    fun inject(fragment: StatisticsFragment)
}