package com.example.tyler.myapplication.agree.view


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.tyler.myapplication.R
import com.example.tyler.myapplication.UiState
import com.example.tyler.myapplication.agree.model.PollModel
import com.example.tyler.myapplication.agree.viewmodel.AgreeFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_agree.*
import kotlinx.android.synthetic.main.poll_list_item.view.*


class AgreeFragment : Fragment() {


    companion object {

        val TAG = AgreeFragment::class.java.simpleName

        fun newInstance() =
                AgreeFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

    lateinit var agreeFragmentViewModel: AgreeFragmentViewModel
    val bin = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agreeFragmentViewModel = ViewModelProviders.of(this).get(AgreeFragmentViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (agreeFragmentViewModel.getUiStateChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            showLoadingState()
                        }
                        is UiState.ListReady -> {
                            showLoadedState(uiState)
                        }
                        is UiState.WebPage -> {
                            showWebPage(uiState)
                        }
                        is UiState.Error -> {
                            showErrorState()
                        }
                    }
                },
                        { error ->
                            Log.e(TAG, "uiStateError $error")
                            showErrorState()
                        }))
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Agree"
        agreeFragmentViewModel.loadPolls()
    }

    fun showLoadingState() {
        Log.d(TAG, "showLoadingState")
        progress_bar.visibility = View.VISIBLE
    }

    @Suppress("UNCHECKED_CAST")
    fun showLoadedState(uiState: UiState.ListReady) {
        progress_bar.visibility = View.GONE
        recycler_view.adapter = AgreeAdapter(uiState.list as List<PollModel>)
    }

    fun showWebPage(uiState: UiState.WebPage) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.url))
        startActivity(browserIntent)
    }

    fun showErrorState() {
        progress_bar?.visibility = View.GONE
        Toast.makeText(this.context, "Couldn't load data", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    inner class AgreeAdapter(val list: List<PollModel>) : RecyclerView.Adapter<AgreeAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.poll_list_item_new, parent, false))
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.poll = list.get(position)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            //Basically using this PollModel along with it's backing property as a cheap "viewmodel"
            //todo any real reason for this backing property?
            private var _poll: PollModel? = null
            var poll: PollModel
                get() = _poll!!
                @SuppressLint("SetTextI18n")
                set(value) {
                    _poll = value
                    view.title_textview.text = poll.title
                    view.body_textview.text = poll.body
                    view.source_textview.text = poll.displayUrl
                    view.percent_textView.text = "${poll.percent}%"
                    view.web_button.setOnClickListener {
                        agreeFragmentViewModel.onItemClicked(poll)
                    }

                }
        }
    }


}
