package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.ifmo.statapp.data.db.dao.LessonDao
import ru.ifmo.statapp.data.db.entity.Lesson
import javax.inject.Inject

class LessonIteractorImpl @Inject constructor(
    private val localDao: LessonDao,
    private val remoteDao: LessonDao)
    : LessonIteractor {

    override fun lessons(): Observable<List<Lesson>> {
        return localDao.lessons()
            .subscribeOn(Schedulers.io())
            .mergeWith(
                remoteDao.lessons()
            )
            .toObservable()
    }

    override fun insert(lesson: Lesson): Completable {
        return remoteDao.insert(lesson)
            .andThen(
                localDao
                    .insert(lesson)
                    .subscribeOn(Schedulers.io())
            )
    }
}