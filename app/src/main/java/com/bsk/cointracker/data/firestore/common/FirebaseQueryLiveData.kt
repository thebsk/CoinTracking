package com.bsk.cointracker.data.firestore.common

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import timber.log.Timber

fun <T> Query.livedata(clazz: Class<T>): LiveData<List<T>> {
    return FirebaseQueryLiveData(
        this,
        clazz
    )
}

private class FirebaseQueryLiveData<T>(
    private val query: Query,
    private val clazz: Class<T>
) : LiveData<List<T>>() {

    private var listener: ListenerRegistration? = null

    override fun onActive() {
        super.onActive()

        listener = query.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot?.documents?.map { it.toObject(clazz)!! }
            } else {
                Timber.e(exception)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        listener?.remove()
        listener = null
    }
}