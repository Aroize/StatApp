package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.domain.iteractor.*
import ru.ifmo.statapp.presentation.presenter.*
import javax.inject.Singleton

@Module
object PresenterModule {

    @Singleton
    @Provides
    fun provideGroupsPresenter(groupsIteractor: GroupsIteractor) = GroupsCreatorPresenter(groupsIteractor)

    @Provides
    fun provideStudentListPresenter(studentIteractor: StudentsIteractor) = StudentListPresenter(studentIteractor)

    @Provides
    fun provideLessonListPresenter(lessonIteractor: LessonIteractor) = LessonsListPresenter(lessonIteractor)

    @Provides
    fun provideCreateLessonPresenter(lessonIteractor: LessonIteractor) = CreateLessonPresenter(lessonIteractor)

    @Provides
    fun provideLessonInfoPresenter(
        studentIteractor: StudentsIteractor,
        attendanceIteractor: AttendanceIteractor
    ) = LessonInfoPresenter(studentIteractor, attendanceIteractor)
}