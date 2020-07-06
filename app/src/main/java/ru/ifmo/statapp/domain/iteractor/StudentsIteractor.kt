package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.ifmo.statapp.data.db.entity.Student

interface StudentsIteractor {
    fun students(): Observable<List<Student>>

    fun studentsByGroup(groupId: Long): Observable<List<Student>>

    fun insert(student: Student): Completable
}