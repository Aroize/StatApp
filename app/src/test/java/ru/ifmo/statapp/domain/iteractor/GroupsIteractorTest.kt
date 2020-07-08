package ru.ifmo.statapp.domain.iteractor

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.entity.Group

class GroupsIteractorTest {

    private val scheduler = TestScheduler()
    private val disposable = CompositeDisposable()

    private val localFakeDao = FakeDao()
    private val remoteFakeDao = FakeDao()

    private val groupsIteractor = GroupsIteractorImpl(localFakeDao, remoteFakeDao)

    private var counter = 0

    private val groups = arrayListOf(
        Group().apply { id = 1L; name = "first" },
        Group().apply { id = 2L; name = "second" },
        Group().apply { id = 3L; name = "third" }
    )

    @Test
    fun testInsert() {
        groups.forEach {
            disposable.add(
                groupsIteractor.insertGroup(it)
                    .observeOn(scheduler)
                    .subscribe({
                        counter += 1
                    }, {
                        assert(false)
                    })
            )
        }
    }

    @Test
    fun testGet() {
        var fakeGroups = emptyList<Group>()
        disposable.add(
            groupsIteractor.groups()
                .observeOn(scheduler)
                .subscribe({
                    fakeGroups = it
                }, {
                    assert(false)
                }, {
                    assert(groups == fakeGroups)
                })
        )
    }

    @Test
    fun testDelete() {
        groups.forEach {
            disposable.add(groupsIteractor.deleteGroup(it)
                .observeOn(scheduler)
                .subscribe({
                    counter--
                }, {
                    assert(false)
                })
            )
        }
    }

    @Test
    fun testResults() {
        assert(counter == 0)
    }

    inner class FakeDao : GroupDao {
        val data: ArrayList<Group> = arrayListOf()

        override fun groups(): Single<List<Group>> {
            return Single.create { emitter ->
                if (!emitter.isDisposed)
                    emitter.onSuccess(data)
            }
        }

        override fun delete(group: Group): Completable {
            return Completable.create { emitter ->
                data.remove(group)
                if (!emitter.isDisposed)
                    if (data.none { it.id == group.id })
                        emitter.onComplete()
                    else
                        emitter.onError(RuntimeException("Stub"))
            }
        }

        override fun insert(group: Group): Completable {
            return Completable.create { emitter ->
                data.add(group)
                if (!emitter.isDisposed)
                    if (data.any { it.id == group.id })
                        emitter.onComplete()
                    else
                        emitter.onError(RuntimeException("Stub"))
            }
        }
    }
}