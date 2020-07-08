package ru.ifmo.statapp.presentation.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Student
import ru.ifmo.statapp.presentation.activity.MainActivity
import ru.ifmo.statapp.presentation.presenter.LessonInfoPresenter
import ru.ifmo.statapp.presentation.view.LessonInfoView
import javax.inject.Inject
import javax.inject.Provider

class LessonInfoFragment : MvpAppCompatFragment(), LessonInfoView, View.OnClickListener {

    fun lessonId() = arguments!!.getLong(lessonIdKey)

    @InjectPresenter
    lateinit var presenter: LessonInfoPresenter

    @Inject
    lateinit var presenterProvider: Provider<LessonInfoPresenter>

    @ProvidePresenter
    fun providePresenter(): LessonInfoPresenter = presenterProvider.get()

    private val adapter = StudentAttendanceAdapter()

    private lateinit var mainView: View
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        presenter.getStudents()
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
        saveBtn = mainView.findViewById(R.id.add_holder_btn)
        saveBtn.text = getString(R.string.save_attendance)
        saveBtn.setOnClickListener(this)
        studentRecyclerView = mainView.findViewById(R.id.holder_list)
        studentRecyclerView.adapter = adapter
    }

    override fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showStudents(students: List<Student>) {
        presenter.getAttendance(lessonId())
        adapter.students = students
        adapter.notifyDataSetChanged()
    }

    override fun updateAttendance(students: List<Long>) {
        adapter.attendance = students.toSet()
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        presenter.setAttendance(adapter.attendance, lessonId())
        (activity as MainActivity).onBackPressed()
    }

    companion object {
        const val lessonIdKey = "lesson.id"
    }

    inner class StudentAttendanceHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var student = Student()

        private val studentName = itemView.findViewById<TextView>(R.id.student_name)
        private val studentSurname = itemView.findViewById<TextView>(R.id.student_surname)
        private val studentEmail = itemView.findViewById<TextView>(R.id.student_email)

        init {
            itemView.setOnClickListener {
                if (student.id in adapter.attendance)
                    adapter.attendance -= student.id
                else
                    adapter.attendance += student.id
                adapter.notifyItemChanged(adapter.students.indexOf(student))
            }
        }

        fun bind(student: Student, hasAttendance: Boolean) {
            this.student = student
            if (hasAttendance) {
                itemView.background = ColorDrawable(Color.GREEN)
            } else {
                itemView.background = ColorDrawable(Color.WHITE)
            }
            studentName.text = student.name
            studentSurname.text = student.lastName
            studentEmail.text = student.email
        }
    }

    inner class StudentAttendanceAdapter : RecyclerView.Adapter<StudentAttendanceHolder>() {

        var attendance: Set<Long> = emptySet()

        var students: List<Student> = emptyList()

        override fun getItemCount() = students.size

        override fun onBindViewHolder(holder: StudentAttendanceHolder, position: Int) {
            val itemStudent = students[position]
            val hasAttendance: Boolean = itemStudent.id in attendance
            holder.bind(itemStudent, hasAttendance)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAttendanceHolder {
            val itemView = layoutInflater.inflate(R.layout.holder_student, parent, false)
            return StudentAttendanceHolder(itemView)
        }
    }
}