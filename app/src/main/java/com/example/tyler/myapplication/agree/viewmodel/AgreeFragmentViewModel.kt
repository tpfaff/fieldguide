package com.example.tyler.myapplication.agree.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tyler.myapplication.UiState
import com.example.tyler.myapplication.agree.model.PollModel
import com.example.tyler.myapplication.agree.model.PollsReadyListener
import com.example.tyler.myapplication.agree.model.PollsRepo
import com.google.firebase.database.DatabaseError
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class AgreeFragmentViewModel : ViewModel() {

    private val uiStateChanged = PublishSubject.create<UiState>()


    companion object {
        val TAG = AgreeFragmentViewModel::class.java.simpleName
    }


    fun loadPolls() {
        Log.d(TAG, "loadPolls")
        uiStateChanged.onNext(UiState.Loading())

        PollsRepo.getPolls(object : PollsReadyListener {
            override fun onPollsReady(list: List<PollModel>) {
                Log.d(TAG, "onPollsReady $list")
                uiStateChanged.onNext(UiState.ListReady(list))
            }

            override fun onGetPollsCancelled(databaseError: DatabaseError) {
                uiStateChanged.onNext(UiState.Error())
            }
        })
    }

    fun onItemClicked(pollModel: PollModel) {
        PollsRepo.getPolls(object : PollsReadyListener {
            override fun onPollsReady(list: List<PollModel>) {
                uiStateChanged.onNext(UiState.WebPage(pollModel.fullUrl))
            }

            override fun onGetPollsCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onItemClicked -> onGetPollsCancelled", databaseError.toException())
            }
        })
    }

    fun getUiStateChanged(): Observable<UiState> {
        return uiStateChanged.hide()
    }

    override fun onCleared() {
        super.onCleared()
    }
}