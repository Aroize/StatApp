package ru.ifmo.statapp.presentation.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.data.db.entity.Lesson
import ru.ifmo.statapp.domain.iteractor.LessonIteractor
import ru.ifmo.statapp.presentation.view.CreateLessonView
import javax.inject.Inject

class CreateLessonPresenter @Inject constructor(
    private val lessonIteractor: LessonIteractor
) : MvpPresenter<CreateLessonView>() {

    private val disposable = CompositeDisposable()

    fun createLesson(
        theme: String,
        hometask: String,
        zoomLink: String,
        timestamp: Long
    ) {
        val lesson = Lesson().also {
            it.theme = theme
            it.hometask = hometask
            it.zoomLink = zoomLink
            it.timestamp = timestamp
        }
        disposable.add(
            lessonIteractor.insert(lesson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState?.successfulInsert()
                }, { e ->
                    viewState?.showError(e.message)
                })
        )
    }
}