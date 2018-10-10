package com.example.tyler.myapplication.organizations.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tyler.myapplication.UiState
import com.example.tyler.myapplication.organizations.model.OrganizationModel
import com.example.tyler.myapplication.organizations.model.OrganizationsReadyListener
import com.example.tyler.myapplication.organizations.model.OrganizationsRepo
import com.google.firebase.database.DatabaseError
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class OrganizationFragmentViewModel : ViewModel(), OrganizationsReadyListener {
    private val uiState = PublishSubject.create<UiState>()

    fun loadOrgs() {
        OrganizationsRepo.getOrgs(this)
    }

    fun getUiStateChanged(): Observable<UiState> {
        return uiState.hide()
    }

    override fun onOrgsReady(list: List<OrganizationModel>) {
        uiState.onNext(UiState.ListReady(list))
    }

    override fun onGetOrgsCancelled(databaseError: DatabaseError) {
        uiState.onNext(UiState.Error())
    }
}