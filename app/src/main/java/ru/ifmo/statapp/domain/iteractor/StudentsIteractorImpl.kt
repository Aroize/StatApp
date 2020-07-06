package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.data.db.entity.Student
import javax.inject.Inject

class StudentsIteractorImpl @Inject constructor(
    private val localDao: StudentDao,
    private val remoteDao: StudentDao
): StudentsIteractor {
    override fun students(): Observable<List<Student>> {
        return localDao.students()
            .subscribeOn(Schedulers.io())
            .mergeWith(
                remoteDao.students()
            )
            .toObservable()
    }

    override fun studentsByGroup(groupId: Long): Observable<List<Student>> {
        return localDao.studentsByGroup(groupId)
            .subscribeOn(Schedulers.io())
            .mergeWith(
                remoteDao.studentsByGroup(groupId)
            )
            .toObservable()
    }

    override fun insert(student: Student): Completable {
        return remoteDao.insert(student)
            .andThen(
                localDao
                    .insert(student)
                    .subscribeOn(Schedulers.io())
            )
    }
}