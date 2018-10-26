package com.example.tyler.myapplication.organizations.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.tyler.myapplication.R
import com.example.tyler.myapplication.UiState
import com.example.tyler.myapplication.organizations.model.OrganizationModel
import com.example.tyler.myapplication.organizations.viewmodel.OrganizationFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_organizations.*
import kotlinx.android.synthetic.main.org_list_item.view.*

class OrganizationsFragment : Fragment() {
    companion object {
        val TAG = OrganizationsFragment::class.java.simpleName

        fun newInstance(): OrganizationsFragment {
            return OrganizationsFragment()
        }
    }

    lateinit var viewModel: OrganizationFragmentViewModel
    lateinit var adapter: OrganizationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrganizationFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_organizations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Organizations"

        viewModel.getUiStateChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            showLoadingState()
                        }
                        is UiState.ListReady -> {
                            showListReadyState(uiState)
                        }
                        is UiState.Error -> {
                            showErrorState()
                        }
                        is UiState.WebPage -> {
                            showWebPage(uiState)
                        }
                    }
                }, { error ->
                    Log.e(TAG, "Failed to get ui state", error)
                })

    }

    override fun onStart() {
        super.onStart()
        viewModel.loadOrgs()
    }

    private fun showLoadingState() {
        progress_bar.visibility = View.VISIBLE
    }

    @Suppress("UNCHECKED_CAST")
    private fun showListReadyState(uiState: UiState.ListReady) {
        progress_bar.visibility = View.GONE
        adapter = OrganizationsAdapter(uiState.list as List<OrganizationModel>)
        recycler_view.adapter = adapter
    }

    private fun showErrorState() {
        progress_bar.visibility = View.GONE

    }

    fun showWebPage(uiState: UiState.WebPage) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.url))
        startActivity(browserIntent)
    }

    inner class OrganizationsAdapter(val organizations: List<OrganizationModel>) : RecyclerView.Adapter<OrganizationsAdapter.OrganizationViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationViewHolder {
            return OrganizationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.org_list_item, parent, false));
        }

        override fun getItemCount(): Int {
            return organizations.size
        }

        override fun onBindViewHolder(holder: OrganizationViewHolder, position: Int) {
            holder.organization = organizations.get(position)
        }

        inner class OrganizationViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            private var _organization: OrganizationModel? = null
            private val OUR_REVOLUTION = "Our Revolution"
            private val DSA = "Democratic Socialists of America"
            var organization: OrganizationModel
                get() = _organization!!
                set(value) {
                    _organization = value
                    view.title_textview.text = organization.name
                    view.body_textview.text = organization.summary
                    view.web_button.setOnClickListener {
                        showWebPage(UiState.WebPage(organization.url))
                    }
                    when (organization.name) {
                        OUR_REVOLUTION -> {
                            view.org_imageview.setImageDrawable(view.resources.getDrawable(R.drawable.or))
                        }
                        DSA -> {
                            view.org_imageview.setImageDrawable(view.resources.getDrawable(R.drawable.dsa))
                        }
                        else ->
                            view.org_imageview.setImageDrawable(view.resources.getDrawable(R.drawable.justdems))

                    }
                }
        }
    }
}