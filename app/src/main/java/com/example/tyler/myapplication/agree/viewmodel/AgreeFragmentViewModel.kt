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
        PollsRepo.getPolls(this)
    }
    
    fun loadUrl(){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse())
        startActivity(browserIntent)
    }

    override fun onCleared() {
        super.onCleared()
    }
}