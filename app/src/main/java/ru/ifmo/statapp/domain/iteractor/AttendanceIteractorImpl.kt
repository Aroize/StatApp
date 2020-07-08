package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.ifmo.statapp.data.db.dao.AttendanceDao
import ru.ifmo.statapp.data.db.entity.Attendance
import javax.inject.Inject

class AttendanceIteractorImpl
    @Inject constructor(
        private val localDao: AttendanceDao,
        private val remoteDao: AttendanceDao
    ): AttendanceIteractor {

    override fun attendance(): Observable<List<Attendance>> {
        return localDao.allAttendance()
            .subscribeOn(Schedulers.io())
            .mergeWith(
                remoteDao.allAttendance()
            )
            .toObservable()
    }

    override fun attendanceByLesson(lessonId: Long): Observable<List<Attendance>> {
        return localDao.lessonAttendance(lessonId)
            .subscribeOn(Schedulers.io())
            .mergeWith(
                remoteDao.lessonAttendance(lessonId)
            )
            .toObservable()
    }

    override fun delete(attendance: Attendance): Completable {
        return remoteDao.delete(attendance)
            .andThen(
                localDao
                    .delete(attendance)
                    .subscribeOn(Schedulers.io())
            )
    }

    override fun insert(attendance: Attendance): Completable {
        return remoteDao.insert(attendance)
            .andThen(
                localDao
                    .insert(attendance)
                    .subscribeOn(Schedulers.io())
            )
    }
}