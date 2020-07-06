package ru.ifmo.statapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "students")
class Student {
    @PrimaryKey
    @get: PropertyName("student_id")
    @set: PropertyName("student_id")
    var id: Long = 0

    var name: String = ""

    var email: String = ""

    @get: PropertyName("lastname")
    @set: PropertyName("lastname")
    var lastName: String = ""

    @get: PropertyName("group_id")
    @set: PropertyName("group_id")
    var groupId: Long = 0
}