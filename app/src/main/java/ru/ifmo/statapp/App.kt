package ru.ifmo.statapp

import android.app.Application
import ru.ifmo.statapp.dagger.ApplicationComponent
import ru.ifmo.statapp.dagger.DaggerApplicationComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
    }

    companion object {
        lateinit var appComponent: ApplicationComponent
    }
}