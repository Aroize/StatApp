package ru.ifmo.statapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "lessons")
class Lesson {
    @PrimaryKey
    @get: PropertyName("lesson_id")
    @set: PropertyName("lesson_id")
    var id: Long = 0

    @get: PropertyName("teacher_id")
    @set: PropertyName("teacher_id")
    var teacherId: Long = 0

    @get: PropertyName("zoom_link")
    @set: PropertyName("zoom_link")
    var zoomLink: String = ""

    var timestamp: Long = 0

    var hometask: String = ""

    var theme: String = ""

    var mandatory: Boolean = false
}