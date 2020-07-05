package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.ifmo.statapp.data.db.entity.Group

interface GroupsIteractor {
    fun groups(): Observable<List<Group>>

    fun insertGroup(group: Group): Completable

    fun deleteGroup(group: Group): Completable
}