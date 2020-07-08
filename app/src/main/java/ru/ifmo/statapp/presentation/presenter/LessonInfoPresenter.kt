package ru.ifmo.statapp.presentation.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.data.db.entity.Attendance
import ru.ifmo.statapp.domain.iteractor.AttendanceIteractor
import ru.ifmo.statapp.domain.iteractor.StudentsIteractor
import ru.ifmo.statapp.presentation.view.LessonInfoView
import javax.inject.Inject

class LessonInfoPresenter @Inject constructor(
    private val studentsIteractor: StudentsIteractor,
    private val attendanceIteractor: AttendanceIteractor
) : MvpPresenter<LessonInfoView>() {

    private var lastAttendance = emptySet<Long>()
    private var attendanceEntities: List<Attendance> = emptyList()

    private val disposable = CompositeDisposable()

    fun getStudents() {
        disposable.add(
            studentsIteractor.students()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ studentList ->
                    viewState?.showStudents(studentList)
                }, {
                    viewState?.showError(it.message)
                }, {})
        )
    }

    fun getAttendance(lessonId: Long) {
        disposable.add(
            attendanceIteractor.attendanceByLesson(lessonId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ attendance ->
                    attendanceEntities = attendance
                    val attendanceList = attendance.map { it.studentId }
                    viewState?.updateAttendance(attendanceList)
                    lastAttendance = attendanceList.toSet()
                }, {
                    viewState?.showError(it.message)
                }, {})
        )
    }

    fun setAttendance(attendance: Set<Long>, lessonId: Long) {
        val addToAttendance = attendance subtract lastAttendance
        val remoteFromAttendance = lastAttendance subtract attendance
        val attendanceToRemove = attendanceEntities.filter { it.studentId in remoteFromAttendance && it.lessonId == lessonId }
        addToAttendance.forEach { studentId ->
            val newAttendance = Attendance().apply {
                id = System.currentTimeMillis()
                this.studentId = studentId
                this.lessonId = lessonId
            }
            disposable.add(
                attendanceIteractor.insert(newAttendance)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
        attendanceToRemove.forEach {
            attendanceIteractor.delete(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }
}