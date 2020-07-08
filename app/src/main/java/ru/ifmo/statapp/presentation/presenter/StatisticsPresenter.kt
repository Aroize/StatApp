package ru.ifmo.statapp.presentation.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.domain.iteractor.AttendanceIteractor
import ru.ifmo.statapp.domain.iteractor.LessonIteractor
import ru.ifmo.statapp.domain.iteractor.StudentsIteractor
import ru.ifmo.statapp.presentation.view.StatisticsView
import javax.inject.Inject

class StatisticsPresenter
    @Inject constructor(
        private val studentIteractor: StudentsIteractor,
        private val attendanceIteractor: AttendanceIteractor
    )
    : MvpPresenter<StatisticsView>() {

    private val disposable = CompositeDisposable()

    fun getStudents() {
        disposable.add(
            studentIteractor.students()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState?.showStudents(it)
                }, {}, {})
        )
    }

    fun getStudentAttendance(studentId: Long) {
        var count = 0
        disposable.add(
            attendanceIteractor.attendanceByStudent(studentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    count = it.size
                }, {}, {
                    viewState?.showStatistics(count)
                })
        )
    }
}