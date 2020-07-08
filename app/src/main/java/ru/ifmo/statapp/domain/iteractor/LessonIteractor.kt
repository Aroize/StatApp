package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.ifmo.statapp.data.db.entity.Lesson

interface LessonIteractor {
    fun lessons(): Observable<List<Lesson>>

    fun insert(lesson: Lesson): Completable
}