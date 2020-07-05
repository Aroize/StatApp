package ru.ifmo.statapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "groups")
class Group {
    @PrimaryKey
    @get: PropertyName("group_id")
    @set: PropertyName("group_id")
    var id: Long = 0

    @get: PropertyName("group_name")
    @set: PropertyName("group_name")
    var name: String = ""
}