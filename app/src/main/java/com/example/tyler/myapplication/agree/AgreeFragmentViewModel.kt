package com.example.tyler.myapplication.agree

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AgreeFragmentViewModel : ViewModel(), PollsReadyListener {

    val uiStateChanged = PublishSubject.create<UiState>()

    companion object {
        val TAG = AgreeFragmentViewModel::class.java.simpleName
    }


    override fun onPollsReady(list: Single<List<PollModel?>>) {
        Log.d(TAG, "onPollsReady $list")
        list.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ list ->
                    uiStateChanged.onNext(UiState.ListReady(list))
                },
                        { throwable ->
                            Log.e(TAG, "error: $throwable")
                        })
    }

    override fun onGetPollsCancelled(databaseError: DatabaseError) {
        uiStateChanged.onNext(UiState.Error())
    }

    fun loadPolls() {
        Log.d(TAG, "loadPolls")
        uiStateChanged.onNext(UiState.Loading())
        PollsDataModel.getPolls(this)
    }

    override fun onCleared() {
        super.onCleared()
    }
}