package ru.ifmo.statapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.entity.Attendance

@Dao
interface AttendanceDao {

    @Query("SELECT * FROM attendance")
    fun allAttendance(): Single<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId")
    fun studentAttendance(studentId: Long): Single<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE lessonId = :lessonId")
    fun lessonAttendance(lessonId: Long): Single<List<Attendance>>

    @Insert
    fun insert(attendance: Attendance): Completable

    @Delete
    fun delete(attendance: Attendance): Completable
}