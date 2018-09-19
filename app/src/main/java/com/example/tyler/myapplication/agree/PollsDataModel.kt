package com.example.tyler.myapplication.agree

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable

class PollsDataModel {
    companion object {
        val TAG = PollsDataModel::class.java.simpleName

        /***
         * Function to fetch the /polls root
         * And map all it's children into a List<PollModel>
         * That is sorted by highest % agreement to lowest
         * An instance of a PollsReadyListener is invoked with the result
         */
        fun getPolls(listener: PollsReadyListener) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val pollsRef = database.getReference("polls")
            pollsRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: $error")
                    listener.onGetPollsCancelled(error)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(AgreeFragmentViewModel.TAG, "onDataChange: $dataSnapshot")
                    val data = ArrayList<PollModel>()
                    listener.onPollsReady(Observable.fromIterable(dataSnapshot.children)
                            .map { child -> child.getValue(PollModel::class.java) }
                            .toSortedList { first, second ->
                                second!!.percent.toInt() - first!!.percent.toInt()
                            })
                }
            })
        }
    }
}