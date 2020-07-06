package ru.ifmo.statapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.data.db.entity.Student

@Database(entities = [Group::class, Student::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao

    abstract fun studentDao(): StudentDao
}