package ru.ifmo.statapp.presentation.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.data.db.entity.Student
import ru.ifmo.statapp.domain.iteractor.StudentsIteractor
import ru.ifmo.statapp.presentation.view.StudentListView
import javax.inject.Inject

class StudentListPresenter @Inject constructor(private val studentsIteractor: StudentsIteractor) :
    MvpPresenter<StudentListView>() {

    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    fun getGroups(groupId: Long) {
        disposable.add(
            studentsIteractor.studentsByGroup(groupId)
                .subscribe({ studentList ->
                    viewState?.showStudents(studentList)
                }, {
                    viewState?.showError(it.message)
                }, {
                    Log.d("Presenter", "Bruh")
                })
        )
    }

    fun insertStudent(
        name: String,
        surname: String,
        email: String,
        groupId: Long
    ) {
        val student = Student().also {
            it.id = System.currentTimeMillis()
            it.name = name
            it.lastName = surname
            it.email = email
            it.groupId = groupId
        }
        disposable.add(
            studentsIteractor.insert(student)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState?.addStudent(student)
                }, {
                    viewState?.showError(it.message)
                })
        )
    }
}