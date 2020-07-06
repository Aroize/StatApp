package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.App
import ru.ifmo.statapp.data.db.FireStoreWrapper
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.data.db.entity.Student
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGroupDao(): GroupDao = App.appDatabase.groupDao()

    @Singleton
    @Provides
    fun provideStudentDao(): StudentDao = App.appDatabase.studentDao()

    @Singleton
    @Provides
    fun provideFirestore(): FireStoreWrapper = FireStoreWrapper()
}