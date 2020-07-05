package ru.ifmo.statapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.entity.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun groups(): Single<List<Group>>

    @Insert
    fun insert(group: Group): Completable

    @Delete
    fun delete(group: Group): Completable
}