package com.example.tyler.myapplication.agree


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

import com.example.tyler.myapplication.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_agree.*

class AgreeFragment : Fragment() {


    companion object {

        val TAG = AgreeFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AgreeFragment.
         */
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

        (agreeFragmentViewModel.uiStateChanged
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agree, container, false)
    }


    override fun onStart() {
        super.onStart()
        agreeFragmentViewModel.loadPolls()
    }

    fun showLoadingState() {
        Log.d(TAG, "showLoadingState")
        progress_bar.visibility = View.VISIBLE
    }

    fun showLoadedState(uiState: UiState.ListReady) {
        progress_bar.visibility = View.GONE
        recycler_view.adapter = AgreeAdapter(uiState.list)
    }

    fun showErrorState() {
        progress_bar.visibility = View.GONE
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

    class AgreeAdapter(val list: List<PollModel?>) : RecyclerView.Adapter<AgreeAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.poll_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.titleTextView.setText(list.get(position)?.title)
            holder.bodyTextView.setText(list.get(position)?.body)
            holder.sourceTextView.setText(list.get(position)?.displayUrl)
            holder.percentTextView.setText(list.get(position)?.percent + "%")
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTextView = view.findViewById<TextView>(R.id.title_textview)!!
            val bodyTextView = view.findViewById<TextView>(R.id.body_textview)!!
            val sourceTextView = view.findViewById<TextView>(R.id.source_textview)!!
            val percentTextView = view.findViewById<TextView>(R.id.percent_textView)!!
        }
    }

}
