package ru.ifmo.statapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ifmo.statapp.R
import ru.ifmo.statapp.domain.MainState
import ru.ifmo.statapp.domain.MainStateAcceptor
import ru.ifmo.statapp.presentation.fragment.GroupCreatorFragment
import ru.ifmo.statapp.presentation.fragment.PickerFragment

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
        val fragment = when (state) {
            MainState.PICKER -> { PickerFragment() }
            MainState.LIST_LESSONS -> { throw NotImplementedError("Stub") }
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
}