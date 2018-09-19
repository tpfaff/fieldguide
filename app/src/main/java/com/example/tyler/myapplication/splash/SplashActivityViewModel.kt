package com.example.tyler.myapplication.splash

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.PublishSubject

class SplashActivityViewModel : ViewModel(){


    var counter = 0
    val timesToRepeat = 2
    val playAnimation = PublishSubject.create<Unit>()
    val startMainActivity = PublishSubject.create<Unit>()

    fun loadAnimation(){
        playAnimation.onNext(Unit)
    }

    fun onAnimationEnd(){
        counter++
        if(counter < timesToRepeat){
            playAnimation.onNext(Unit)
        }else{
            startMainActivity.onNext(Unit)
        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}