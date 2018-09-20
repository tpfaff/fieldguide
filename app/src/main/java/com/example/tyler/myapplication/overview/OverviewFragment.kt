package com.example.tyler.myapplication.overview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.tyler.myapplication.R
import com.example.tyler.myapplication.agree.AgreeFragment
import com.example.tyler.myapplication.ext.runDelayedOnUiThread
import com.example.tyler.myapplication.ext.toBitmap
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.overview_list_item.*

class OverviewFragment : Fragment() {

    companion object {
        val TAG = OverviewFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return OverviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overview, container, false);
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewItemViewHolder {
            return OverviewItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.overview_list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return 3;
        }

        override fun onBindViewHolder(holder: OverviewItemViewHolder, position: Int) {
            when (position) {
                0 -> {
                    holder.animationView.setAnimation(R.raw.bar_graph)
                    holder.animationView.playAnimation()
//                    createPaletteSync(holder.animationView.background.toBitmap()).lightVibrantSwatch?.rgb?.let {
//                        holder.animationView.setBackgroundColor(it)
//                    }
                }
                1 -> {
                    holder.animationView.setAnimation(R.raw.favorite)
                    runDelayedOnUiThread({ holder.animationView.playAnimation() }, 500)
                }
                2 -> {
                    holder.animationView.setAnimation(R.raw.phone)
                    runDelayedOnUiThread({ holder.animationView.playAnimation() }, 800)
                }
            }
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
            return super.getItemViewType(position)
        }

        inner class OverviewItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)!!

            init {
                animationView.setOnClickListener {
                    this@OverviewFragment.fragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.root, AgreeFragment.newInstance(), AgreeFragment.TAG)
                            ?.addToBackStack(AgreeFragment.TAG)
                            ?.commit()
                }
            }
        }
    }
}