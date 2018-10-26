package com.wajumbie.progressivefieldguide.ext

import android.os.Handler
import android.os.Looper

    fun runOnUiThread(block: () -> Unit){
        if(Looper.getMainLooper() == Looper.myLooper()){
            Looper.myLooper().run { block }
        }else{
            Looper.getMainLooper().run { block }
        }
    }

    fun runDelayedOnUiThread(block: () -> Unit, delayMillis: Long){
        if(Looper.getMainLooper() == Looper.myLooper()) {
            Handler().postDelayed(block, delayMillis)
        }else{
            Handler(Looper.getMainLooper()).postDelayed(block, delayMillis)
        }
    }