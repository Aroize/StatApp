package ru.ifmo.statapp.dagger

import dagger.Component
import ru.ifmo.statapp.presentation.activity.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [IteractorModule::class])
interface ApplicationComponent {
    fun inject(activity: LoginActivity)
}