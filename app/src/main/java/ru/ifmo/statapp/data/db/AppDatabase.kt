package ru.ifmo.statapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ifmo.statapp.data.db.dao.AttendanceDao
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.dao.LessonDao
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.data.db.entity.Attendance
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.data.db.entity.Lesson
import ru.ifmo.statapp.data.db.entity.Student

@Database(entities = [Group::class, Student::class, Lesson::class, Attendance::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao

    abstract fun studentDao(): StudentDao

    abstract fun lessonDao(): LessonDao

    abstract fun attendanceDao(): AttendanceDao
}