package ru.ifmo.statapp.data.db

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.db.dao.GroupDao
import ru.ifmo.statapp.data.db.entity.Group
import java.lang.RuntimeException

class FireStoreWrapper : GroupDao {

    private val firestore = FirebaseFirestore.getInstance()

    override fun groups(): Single<List<Group>> {
        val snapshot = firestore.collection("groups").get()
        return Single.create { emitter ->
            try {
                snapshot.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val result = arrayListOf<Group>()
                        task.result!!.forEach { document ->
                            result.add(document.toObject(Group::class.java))
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

    override fun insert(group: Group): Completable {
        val insertTask = firestore.collection("groups").add(group)
        return Completable.create { emitter ->
            try {
                if (insertTask.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(RuntimeException(insertTask.exception))
            } catch (t: Throwable) {
                Log.d(tag, "Exception while getting result for insert", t)
                emitter.onError(t)
            }
        }
    }

    override fun delete(group: Group): Completable {
        val removeTask = firestore.collection("groups").whereEqualTo("group_id", group.id)
        return Completable.create { emitter ->
            try {
                removeTask.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            task.result?.forEach { document ->
                                document.reference.delete()
                            }
                            Log.d(tag, "Successfully deleted group documents")
                            emitter.onComplete()
                        } else {
                            Log.d(tag, "Deleting groups task is unsuccessful")
                            emitter.onError(RuntimeException(task.exception))
                        }
                    }
                    .addOnFailureListener { e ->
                        emitter.onError(e)
                    }
            } catch (t: Throwable) {

            }
        }
    }

    companion object {
        const val tag = "FireStoreWrapper"
    }
}