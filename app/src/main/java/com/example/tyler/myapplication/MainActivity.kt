package com.example.tyler.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tyler.myapplication.overview.OverviewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, OverviewFragment.newInstance())
                .commit()
    }
}
