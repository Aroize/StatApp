package ru.ifmo.statapp.data.db

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.dao.AttendanceDao
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.dao.LessonDao
import ru.ifmo.statapp.data.db.dao.StudentDao
import ru.ifmo.statapp.data.db.entity.Attendance
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.data.db.entity.Lesson
import ru.ifmo.statapp.data.db.entity.Student
import java.lang.RuntimeException

class FireStoreWrapper : GroupDao, StudentDao, LessonDao, AttendanceDao {

    private val firestore = FirebaseFirestore.getInstance()

    private fun<T> processSnapshot(snapshot: Task<QuerySnapshot>, clazz: Class<T>): Single<List<T>> {
        return Single.create { emitter ->
            try {
                snapshot.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val result = arrayListOf<T>()
                        task.result!!.forEach { document ->
                            result.add(document.toObject(clazz))
                        }
                        Log.d(tag, "Task is successful, groups are ready")
                        emitter.onSuccess(result)
                    } else {
                        Log.d(tag, "Task is unsuccessful", task.exception)
                        emitter.onError(RuntimeException(task.exception))
                    }
                }
                    .addOnFailureListener { e ->
                        emitter.onError(e)
                    }
            } catch (t: Throwable) {
                Log.d(tag, "Exception, while reading groups from firestore", t)
                emitter.onError(t)
            }
        }
    }

    private fun <T, V> whereQuery(collectionPath: String, field: String, value: V, clazz: Class<T>): Single<List<T>> {
        val snapshot = firestore.collection(collectionPath).whereEqualTo(field, value).get()
        return processSnapshot(snapshot, clazz)
    }

    private fun <T> allQuery(collectionPath: String, clazz: Class<T>): Single<List<T>> {
        val snapshot = firestore.collection(collectionPath).get()
        return processSnapshot(snapshot, clazz)
    }

    private fun <T> delete(collectionPath: String, field: String, value: T): Completable {
        val removeTask = firestore.collection(collectionPath).whereEqualTo(field, value)
        return Completable.create { emitter ->
            try {
                removeTask.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            task.result?.forEach { document ->
                                document.reference.delete()
                            }
                            Log.d(tag, "Successfully deleted documents")
                            emitter.onComplete()
                        } else {
                            Log.d(tag, "Deleting task is unsuccessful")
                            emitter.onError(RuntimeException(task.exception))
                        }
                    }
                    .addOnFailureListener { e ->
                        emitter.onError(e)
                    }
            } catch (t: Throwable) {
                Log.d(tag, "Exception while getting result for delete", t)
                emitter.onError(t)
            }
        }
    }

    private fun insert(collectionPath: String, obj: Any): Completable {
        val insertTask = firestore.collection(collectionPath).add(obj)
        return Completable.create { emitter ->
            try {
                insertTask
                    .addOnCompleteListener {
                        if (insertTask.isSuccessful)
                            emitter.onComplete()
                        else
                            emitter.onError(RuntimeException(insertTask.exception))
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
            } catch (t: Throwable) {
                Log.d(tag, "Exception while getting result for insert", t)
                emitter.onError(t)
            }
        }
    }

    override fun groups(): Single<List<Group>> {
        return allQuery("groups", Group::class.java)
    }

    override fun insert(group: Group): Completable {
        return insert("groups", group)
    }

    override fun delete(group: Group): Completable {
        return delete("groups", "group_id", group.id)
    }

    override fun students(): Single<List<Student>> {
        return allQuery("students", Student::class.java)
    }

    override fun insert(student: Student): Completable {
        return insert("students", student)
    }

    override fun delete(student: Student): Completable {
        return delete("students", "student_id", student.id)
    }

    override fun studentById(id: Long): Single<List<Student>> {
        return whereQuery("students", "student_id", id, Student::class.java)
    }

    override fun studentsByGroup(groupId: Long): Single<List<Student>> {
        return whereQuery("students", "group_id", groupId, Student::class.java)
    }

    override fun lessons(): Single<List<Lesson>> {
        return allQuery("lessons", Lesson::class.java)
    }

    override fun insert(lesson: Lesson): Completable {
        return insert("lessons", lesson)
    }

    override fun delete(lesson: Lesson): Completable {
        return delete("lessons", "lesson_id", lesson.id)
    }

    override fun allAttendance(): Single<List<Attendance>> {
        return allQuery("attendance", Attendance::class.java)
    }

    override fun insert(attendance: Attendance): Completable {
        return insert("attendance", attendance)
    }

    override fun delete(attendance: Attendance): Completable {
        return delete("attendance", "attendance_id", attendance.id)
    }

    override fun studentAttendance(studentId: Long): Single<List<Attendance>> {
        return whereQuery("attendance", "student_id", studentId, Attendance::class.java)
    }

    override fun lessonAttendance(lessonId: Long): Single<List<Attendance>> {
        return whereQuery("attendance", "lesson_id", lessonId, Attendance::class.java)
    }

    companion object {
        const val tag = "FireStoreWrapper"
    }
}