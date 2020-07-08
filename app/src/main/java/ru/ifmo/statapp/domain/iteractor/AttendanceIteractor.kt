package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.ifmo.statapp.data.db.entity.Attendance

interface AttendanceIteractor {
    fun attendance(): Observable<List<Attendance>>

    fun attendanceByLesson(lessonId: Long): Observable<List<Attendance>>

    fun attendanceByStudent(studentId: Long): Observable<List<Attendance>>

    fun insert(attendance: Attendance): Completable

    fun delete(attendance: Attendance): Completable
}