package com.example.tyler.myapplication.organizations.model

import com.google.firebase.database.DatabaseError

interface OrganizationsReadyListener {
    fun onOrgsReady(list: List<OrganizationModel>)
    fun onGetOrgsCancelled(databaseError: DatabaseError)
}