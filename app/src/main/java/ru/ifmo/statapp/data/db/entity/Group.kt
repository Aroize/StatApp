package ru.ifmo.statapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
class Group {
    @PrimaryKey
    var id: Long = 0
    lateinit var name: String
}