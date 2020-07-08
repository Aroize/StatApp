package ru.ifmo.statapp.presentation.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.domain.iteractor.LessonIteractor
import ru.ifmo.statapp.presentation.view.LessonListView
import javax.inject.Inject

class LessonsListPresenter @Inject constructor(
    private val lessonIteractor: LessonIteractor
) : MvpPresenter<LessonListView>() {


    private val disposable = CompositeDisposable()

    fun getLessons() {
        disposable.add(
            lessonIteractor.lessons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState?.showLessons(it)
                }, {
                    Log.d("LessonsPresenter", "Exception while requesting lessons", it)
                }, {
                    Log.d("LessonsPresenter", "Bruh")
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}