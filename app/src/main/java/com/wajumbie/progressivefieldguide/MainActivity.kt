package com.wajumbie.progressivefieldguide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wajumbie.progressivefieldguide.overview.view.OverviewFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.root, OverviewFragment.newInstance(),OverviewFragment.TAG)
                    .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
