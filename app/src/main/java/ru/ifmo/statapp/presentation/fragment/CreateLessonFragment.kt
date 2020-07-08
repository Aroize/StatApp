package ru.ifmo.statapp.presentation.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.presentation.activity.MainActivity
import ru.ifmo.statapp.presentation.presenter.CreateLessonPresenter
import ru.ifmo.statapp.presentation.view.CreateLessonView
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class CreateLessonFragment : MvpAppCompatFragment(), CreateLessonView,
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: CreateLessonPresenter

    @Inject
    lateinit var presenterProvider: Provider<CreateLessonPresenter>

    @ProvidePresenter
    fun providePresenter(): CreateLessonPresenter = presenterProvider.get()

    private val calendar = Calendar.getInstance()

    private lateinit var mainView: View
    private lateinit var createBtn: Button
    private lateinit var chooseDateBtn: Button
    private lateinit var chooseTimeBtn: Button
    private lateinit var themeEditText: EditText
    private lateinit var hometaskEditText: EditText
    private lateinit var zoomLinkEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_create_lesson, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        chooseDateBtn = mainView.findViewById(R.id.choose_date_btn)
        chooseDateBtn.setOnClickListener {
            DatePickerDialog(
                activity as Context,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
        chooseTimeBtn = mainView.findViewById(R.id.choose_time_btn)
        chooseTimeBtn.setOnClickListener {
            TimePickerDialog(
                activity,
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
                .show()
        }
        themeEditText = mainView.findViewById(R.id.lesson_theme)
        hometaskEditText = mainView.findViewById(R.id.home_task)
        zoomLinkEditText = mainView.findViewById(R.id.zoom_link)
        createBtn = mainView.findViewById(R.id.create_lesson_btn)
        createBtn.setOnClickListener(this)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
        calendar[Calendar.MINUTE] = minute
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
    }

    override fun onClick(v: View?) {
        val theme = themeEditText.text.toString()
        val hometask = hometaskEditText.text.toString()
        val zoomLink = zoomLinkEditText.text.toString()
        val timestamp = calendar.time.time
        presenter.createLesson(theme, hometask, zoomLink, timestamp)
    }

    override fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun successfulInsert() {
        (activity as MainActivity).onBackPressed()
    }
}