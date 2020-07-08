package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Student
import ru.ifmo.statapp.presentation.presenter.StatisticsPresenter
import ru.ifmo.statapp.presentation.view.StatisticsView
import javax.inject.Inject
import javax.inject.Provider

class StatisticsFragment : MvpAppCompatFragment(), StatisticsView {

    @InjectPresenter
    lateinit var presenter: StatisticsPresenter

    @Inject
    lateinit var presenterProvider: Provider<StatisticsPresenter>

    @ProvidePresenter
    fun providePresenter(): StatisticsPresenter = presenterProvider.get()

    private lateinit var mainView: View

    private lateinit var studentRecyclerView: RecyclerView

    private val adapter = StudentAdapter()

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
        mainView.findViewById<View>(R.id.add_holder_btn).apply { visibility = View.GONE }
        studentRecyclerView = mainView.findViewById(R.id.holder_list)
        studentRecyclerView.adapter = adapter
    }

    override fun showStudents(students: List<Student>) {
        adapter.students = students
        adapter.notifyDataSetChanged()
    }

    override fun showStatistics(count: Int) {
        val text = getString(R.string.attendance, count)
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    open inner class StudentVieHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var student = Student()

        init {
            itemView.setOnClickListener {
                presenter.getStudentAttendance(studentId = student.id)
            }
        }

        private val studentName = itemView.findViewById<TextView>(R.id.student_name)

        private val studentLastName = itemView.findViewById<TextView>(R.id.student_surname)

        private val studentEmail = itemView.findViewById<TextView>(R.id.student_email)

        fun bind(student: Student) {
            this.student = student
            studentName.text = student.name
            studentLastName.text = student.lastName
            studentEmail.text = student.email
        }
    }

    open inner class StudentAdapter : RecyclerView.Adapter<StudentVieHolder>() {

        var students: List<Student> = emptyList()

        override fun getItemCount() = students.size

        override fun onBindViewHolder(holder: StudentVieHolder, position: Int) {
            holder.bind(students[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentVieHolder {
            val itemView = layoutInflater.inflate(R.layout.holder_student, parent, false)
            return StudentVieHolder(itemView)
        }
    }
}