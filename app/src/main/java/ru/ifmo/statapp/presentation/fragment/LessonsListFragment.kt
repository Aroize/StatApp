package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Lesson
import ru.ifmo.statapp.presentation.activity.MainActivity
import ru.ifmo.statapp.presentation.presenter.LessonsListPresenter
import ru.ifmo.statapp.presentation.view.LessonListView
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class LessonsListFragment : MvpAppCompatFragment(), LessonListView, View.OnClickListener {

    private val adapter = LessonAdapter()

    @InjectPresenter
    lateinit var presenter: LessonsListPresenter

    @Inject
    lateinit var presenterProvider: Provider<LessonsListPresenter>

    @ProvidePresenter
    fun providePresenter(): LessonsListPresenter = presenterProvider.get()

    private lateinit var mainView: View
    private lateinit var lessonListRecycler: RecyclerView
    private lateinit var addLessonBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        presenter.getLessons()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_list, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        addLessonBtn = mainView.findViewById(R.id.add_holder_btn)
        addLessonBtn.text = getString(R.string.add_lesson_label)
        addLessonBtn.setOnClickListener(this)
        lessonListRecycler = mainView.findViewById(R.id.holder_list)
        lessonListRecycler.adapter = adapter
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).createLesson()
    }

    override fun showLessons(lessons: List<Lesson>) {
        adapter.lessons = lessons
        adapter.notifyDataSetChanged()
    }

    inner class LessonViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val lessonTheme = itemView.findViewById<TextView>(R.id.lesson_theme)
        private val lessonZoomLink = itemView.findViewById<TextView>(R.id.zoom_link)
        private val lessonTime = itemView.findViewById<TextView>(R.id.lesson_time)

        private var lesson = Lesson()

        fun bind(lesson: Lesson) {
            this.lesson = lesson
            val date = Date(lesson.timestamp)
            lessonTheme.text = lesson.theme
            lessonTime.text = "$date"
            lessonZoomLink.text = lesson.zoomLink
        }
    }

    inner class LessonAdapter : RecyclerView.Adapter<LessonViewHolder>() {

        var lessons: List<Lesson> = emptyList()

        override fun getItemCount() = lessons.size

        override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
            holder.bind(lessons[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
            val itemView = layoutInflater.inflate(R.layout.holder_lesson, parent, false)
            return LessonViewHolder(itemView)
        }
    }
}