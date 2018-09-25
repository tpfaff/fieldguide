package com.example.tyler.myapplication

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
        }else{
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
        }

        //Auto-cache data for offline use
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}