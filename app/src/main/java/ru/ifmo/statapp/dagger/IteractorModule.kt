package ru.ifmo.statapp.dagger

import dagger.Module
import dagger.Provides
import ru.ifmo.statapp.domain.api.LoginIteractor
import ru.ifmo.statapp.domain.iteractor.FakeLoginIteractor

@Module
object IteractorModule {
    @Provides
    fun provideFakeLoginIteractor(): LoginIteractor = FakeLoginIteractor()
}