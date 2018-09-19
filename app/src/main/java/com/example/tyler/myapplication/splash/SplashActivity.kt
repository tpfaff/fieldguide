package com.example.tyler.myapplication.splash

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.tyler.myapplication.MainActivity
import com.example.tyler.myapplication.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    val TAG = this@SplashActivity.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAnimationEnd(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })

    }

    override fun onStop() {
        super.onStop()
    }
}