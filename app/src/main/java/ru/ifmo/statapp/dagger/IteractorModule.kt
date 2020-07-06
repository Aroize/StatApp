package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.data.db.FireStoreWrapper
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.domain.api.LoginIteractor
import ru.ifmo.statapp.domain.iteractor.*

@Module
object IteractorModule {
    @Provides
    fun provideFakeLoginIteractor(): LoginIteractor = FakeLoginIteractor()

    @Provides
    fun provideGroupsIteractor(localDao: GroupDao, remoteDao: FireStoreWrapper): GroupsIteractor = GroupsIteractorImpl(localDao, remoteDao)

    @Provides
    fun provideStudentIteractor(localDao: StudentDao, remoteDao: FireStoreWrapper): StudentsIteractor = StudentsIteractorImpl(localDao, remoteDao)
}