package ru.ifmo.statapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.entity.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM students ORDER BY name, lastName")
    fun students(): Single<List<Student>>

    @Query("SELECT * FROM students WHERE id = :id")
    fun studentById(id: Long): Single<List<Student>>

    @Query("SELECT * FROM students WHERE groupId = :groupId")
    fun studentsByGroup(groupId: Long): Single<List<Student>>

    @Insert
    fun insert(student: Student): Completable

    @Delete
    fun delete(student: Student): Completable
}