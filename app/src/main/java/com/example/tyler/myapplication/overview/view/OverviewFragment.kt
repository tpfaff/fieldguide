package com.example.tyler.myapplication.overview.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.tyler.myapplication.R
import com.example.tyler.myapplication.agree.view.AgreeFragment
import com.example.tyler.myapplication.ext.runDelayedOnUiThread
import com.example.tyler.myapplication.organizations.view.OrganizationsFragment
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {

    companion object {
        val TAG = OverviewFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return OverviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onStart() {
        super.onStart()
        recycler_view.adapter = OverviewAdapter()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class OverviewAdapter : RecyclerView.Adapter<OverviewAdapter.OverviewItemViewHolder>() {

        lateinit var recyclerView: RecyclerView
        val VIEW_TYPE_POLLS = 0
        val VIEW_TYPE_ORGANIZATIONS = 1
        val VIEW_TYPE_REGISTER_TO_VOTE = 2
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewItemViewHolder {
            return OverviewItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.overview_list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return 3
        }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

        override fun onBindViewHolder(holder: OverviewItemViewHolder, position: Int) {
            when (getItemViewType(position)) {
                VIEW_TYPE_POLLS -> {
                    holder.animationView.setAnimation(R.raw.bar_graph)
                    holder.animationView.playAnimation()
//                    createPaletteSync(holder.animationView.background.toBitmap()).lightVibrantSwatch?.rgb?.let {
//                        holder.animationView.setBackgroundColor(it)
//                    }
                }
                VIEW_TYPE_ORGANIZATIONS -> {
                    holder.animationView.setAnimation(R.raw.cubos)
                    holder.title.text = "Organizations"
                    holder.body.text = "Connect with progressive organizations to help make a difference"
                    runDelayedOnUiThread({ holder.animationView.playAnimation() }, 500)
                }
                VIEW_TYPE_REGISTER_TO_VOTE -> {
                    holder.animationView.setAnimation(R.raw.phone)
                    holder.title.text = "Register to Vote"
                    holder.body.text = "Your vote matters"
                    runDelayedOnUiThread({ holder.animationView.playAnimation() }, 800)
                }
            }
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
            this.recyclerView = recyclerView
        }

        // Generate palette synchronously and return it
        fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

        // Generate palette asynchronously and use it on a different
// thread using onGenerated()
        fun createPaletteAsync(bitmap: Bitmap) {
            Palette.from(bitmap).generate { palette ->
                // Use generated instance
            }
        }

        override fun getItemViewType(position: Int): Int {
            when (position) {
                0 -> return VIEW_TYPE_POLLS
                1 -> return VIEW_TYPE_ORGANIZATIONS
                2 -> return VIEW_TYPE_REGISTER_TO_VOTE
                else -> return 0
            }
        }

        inner class OverviewItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)!!
            val title = view.findViewById<TextView>(R.id.title_textview)
            val body = view.findViewById<TextView>(R.id.body_textview)

            init {
                view.setOnClickListener {
                    when (getItemViewType(adapterPosition)) {
                        VIEW_TYPE_POLLS -> {
                            this@OverviewFragment.fragmentManager
                                    ?.beginTransaction()
                                    ?.replace(R.id.root, AgreeFragment.newInstance(), AgreeFragment.TAG)
                                    ?.addToBackStack(AgreeFragment.TAG)
                                    ?.commit()
                        }

                        VIEW_TYPE_ORGANIZATIONS -> {
                            this@OverviewFragment.fragmentManager
                                    ?.beginTransaction()
                                    ?.replace(R.id.root, OrganizationsFragment.newInstance(), OrganizationsFragment.TAG)
                                    ?.addToBackStack(OrganizationsFragment.TAG)
                                    ?.commit()
                        }

                        VIEW_TYPE_REGISTER_TO_VOTE -> {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.register_url)))
                            startActivity(browserIntent)
                        }
                    }
                }

            }
        }


    }
}
