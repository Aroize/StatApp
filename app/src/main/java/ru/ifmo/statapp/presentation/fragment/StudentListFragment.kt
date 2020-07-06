package ru.ifmo.statapp.presentation.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import ru.ifmo.statapp.presentation.presenter.StudentListPresenter
import ru.ifmo.statapp.presentation.view.StudentListView
import javax.inject.Inject
import javax.inject.Provider

class StudentListFragment : MvpAppCompatFragment(), StudentListView, View.OnClickListener {

    private val adapter = StudentAdapter()

    fun groupId() = arguments!!.getLong(groupIdKey)

    @InjectPresenter
    lateinit var presenter: StudentListPresenter

    @Inject
    lateinit var presenterProvider: Provider<StudentListPresenter>

    @ProvidePresenter
    fun providePresenter(): StudentListPresenter = presenterProvider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        presenter.getGroups(groupId())
    }

    private lateinit var studentRecycler: RecyclerView
    private lateinit var addStudentBtn: Button

    private lateinit var mainView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_student_group_list, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        studentRecycler = mainView.findViewById(R.id.student_list)
        studentRecycler.adapter = adapter
        addStudentBtn = mainView.findViewById(R.id.add_student_btn)
        addStudentBtn.setOnClickListener(this)
    }

    override fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showStudents(studentList: List<Student>) {
        adapter.students = studentList
        adapter.notifyDataSetChanged()
    }

    override fun addStudent(student: Student) {
        adapter.students += student
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("InflateParams")
    override fun onClick(v: View?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_student, null)
        AlertDialog.Builder(activity)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                val name = dialogView.findViewById<TextView>(R.id.student_field_name).text.toString()
                val surname = dialogView.findViewById<TextView>(R.id.student_field_surname).text.toString()
                val email = dialogView.findViewById<TextView>(R.id.student_field_email).text.toString()
                presenter.insertStudent(name, surname, email, groupId())
            }
            .show()
    }

    companion object {
        const val groupIdKey = "group.id"
    }

    inner class StudentVieHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var student = Student()

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

    inner class StudentAdapter : RecyclerView.Adapter<StudentVieHolder>() {

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