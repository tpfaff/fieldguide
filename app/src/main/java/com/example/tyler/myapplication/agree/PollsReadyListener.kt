package com.example.tyler.myapplication.agree

import com.google.firebase.database.DatabaseError
import io.reactivex.Observable
import io.reactivex.Single

interface PollsReadyListener{
    fun onPollsReady(list: Single<List<PollModel?>>)
    fun onGetPollsCancelled(databaseError: DatabaseError)
}