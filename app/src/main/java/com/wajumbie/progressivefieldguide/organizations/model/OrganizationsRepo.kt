package com.wajumbie.progressivefieldguide.organizations.model

import android.util.Log
import com.wajumbie.progressivefieldguide.agree.model.PollModel
import com.wajumbie.progressivefieldguide.agree.model.PollsRepo
import com.wajumbie.progressivefieldguide.agree.viewmodel.AgreeFragmentViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrganizationsRepo {
    companion object {
        val TAG = PollsRepo::class.java.simpleName

        /***
         * Function to fetch the /organizations root
         * And map all it's children into a List<OrganizationModel>
         * An instance of a OrganizationsReadyListener is invoked with the result
         */
        fun getOrgs(listener: OrganizationsReadyListener) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val pollsRef = database.getReference("organizations")
            pollsRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: $error")
                    listener.onGetOrgsCancelled(error)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(AgreeFragmentViewModel.TAG, "onDataChange: $dataSnapshot")
                    val data = ArrayList<PollModel>()
                    listener.onOrgsReady(dataSnapshot.children
                            .map { child -> child.getValue(OrganizationModel::class.java)!! })
                }
            })
        }
    }
}