package ru.ifmo.statapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.data.db.entity.Lesson
import ru.ifmo.statapp.domain.MainState
import ru.ifmo.statapp.domain.MainStateAcceptor
import ru.ifmo.statapp.presentation.fragment.*

class MainActivity : AppCompatActivity(), MainStateAcceptor {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PickerFragment())
            .commitNow()
    }

    override fun acceptState(state: MainState) {
        val fragment: Fragment = when (state) {
            MainState.PICKER -> { PickerFragment() }
            MainState.LIST_LESSONS -> { LessonsListFragment() }
            MainState.CREATE_GROUP -> { GroupCreatorFragment() }
            MainState.STATISTICS -> { throw NotImplementedError("Stub") }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment !is PickerFragment)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PickerFragment())
                .commitNow()
        else
            super.onBackPressed()
    }

    fun showGroup(group: Group) {
        val fragment = StudentListFragment().apply {
            arguments = Bundle().also {
                    bundle ->  bundle.putLong(StudentListFragment.groupIdKey, group.id)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    fun createLesson() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CreateLessonFragment())
            .commitNow()
    }

    fun showLesson(lesson: Lesson) {
        val fragment = LessonInfoFragment().apply {
            arguments = Bundle().also {
                bundle -> bundle.putLong(LessonInfoFragment.lessonIdKey, lesson.id)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    companion object {
        fun createIntent(packageContext: Context) = Intent(packageContext, MainActivity::class.java)
    }
}