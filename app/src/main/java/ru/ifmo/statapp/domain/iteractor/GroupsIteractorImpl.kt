package ru.ifmo.statapp.domain.iteractor

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.entity.Group
import javax.inject.Inject

class GroupsIteractorImpl
@Inject constructor(
    private val localGroupsDao: GroupDao,
    private val remoteGroupsDao: GroupDao
) : GroupsIteractor {
    override fun groups(): Observable<List<Group>> = Observable.create { emitter ->
        localGroupsDao.groups()
            .subscribeOn(Schedulers.io())
            .subscribe({ localGroups ->
                if (!emitter.isDisposed) {
                    Log.d(tag, "Local groups are ready")
                    emitter.onNext(localGroups)
                } else
                    Log.d(tag, "Remote host was faster")
            }, { e ->
                Log.d(tag, "Error while reading from local db, trying to use only remote", e)
            })
        remoteGroupsDao.groups()
            .subscribeOn(Schedulers.io())
            .subscribe({ remoteGroups ->
                Log.d(tag, "Remote groups are ready")
                emitter.onNext(remoteGroups)
                emitter.onComplete()
            }, { e ->
                Log.d(tag, "Error while reading from remote db", e)
                emitter.onError(e)
            })
    }

    override fun insertGroup(group: Group): Completable {
        return remoteGroupsDao
            .insert(group)
            .subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .andThen(
                localGroupsDao
                    .insert(group)
                    .subscribeOn(Schedulers.io())
            )
    }

    override fun deleteGroup(group: Group): Completable {
        return remoteGroupsDao
            .delete(group)
            .subscribeOn(Schedulers.io())
            .andThen(
                localGroupsDao
                    .delete(group)
                    .subscribeOn(Schedulers.io())
            )
    }

    companion object {
        const val tag = "GroupsIteractorImpl"
    }
}