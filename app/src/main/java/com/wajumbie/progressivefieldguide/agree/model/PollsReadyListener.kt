package com.wajumbie.progressivefieldguide.agree.model

import com.google.firebase.database.DatabaseError

interface PollsReadyListener{
    fun onPollsReady(list: List<PollModel>)
    fun onGetPollsCancelled(databaseError: DatabaseError)
}