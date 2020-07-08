package ru.ifmo.statapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.entity.Lesson

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons")
    fun lessons(): Single<List<Lesson>>

    @Insert
    fun insert(lesson: Lesson): Completable

    @Delete
    fun delete(lesson: Lesson): Completable
}