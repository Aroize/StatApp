package ru.ifmo.statapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.entity.Group

@Database(entities = [Group::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}