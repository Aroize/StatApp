package ru.ifmo.statapp

import android.app.Application
import androidx.room.Room
import ru.ifmo.statapp.dagger.ApplicationComponent
import ru.ifmo.statapp.dagger.DaggerApplicationComponent
import ru.ifmo.statapp.data.db.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, databaseName)
            .build()
    }

    companion object {
        lateinit var appComponent: ApplicationComponent

        lateinit var appDatabase: AppDatabase

        private const val databaseName = "database"
    }
}