package com.example.tyler.myapplication.agree.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.tyler.myapplication.agree.model.PollModel
import com.example.tyler.myapplication.agree.model.PollsReadyListener
import com.example.tyler.myapplication.agree.model.PollsRepo
import com.example.tyler.myapplication.agree.model.UiState
import com.google.firebase.database.DatabaseError
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class AgreeFragmentViewModel : ViewModel() {

    val uiStateChanged = PublishSubject.create<UiState>()
    var clickedPostion = -1;

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

    fun onItemClicked(position: Int) {
        PollsRepo.getPolls(object : PollsReadyListener {
            override fun onPollsReady(list: List<PollModel>) {
                uiStateChanged.onNext(UiState.WebPage(list[position].fullUrl))
            }

            override fun onGetPollsCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onItemClicked -> onGetPollsCancelled", databaseError.toException())
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
    }
}