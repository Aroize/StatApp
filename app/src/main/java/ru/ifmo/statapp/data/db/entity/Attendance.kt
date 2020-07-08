package ru.ifmo.statapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "attendance")
class Attendance {
    @PrimaryKey
    @get: PropertyName("attendance_id")
    @set: PropertyName("attendance_id")
    var id: Long = 0

    @get: PropertyName("student_id")
    @set: PropertyName("student_id")
    var studentId: Long = 0

    @get: PropertyName("lesson_id")
    @set: PropertyName("lesson_id")
    var lessonId: Long = 0
}