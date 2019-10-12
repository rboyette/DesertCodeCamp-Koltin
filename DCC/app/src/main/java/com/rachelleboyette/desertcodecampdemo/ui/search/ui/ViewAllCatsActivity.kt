package com.rachelleboyette.desertcodecampdemo.ui.search.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.ui.search.ui.fragment.ViewAllCatsFragment

class ViewAllCatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_all_cats_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ViewAllCatsFragment(), ViewAllCatsFragment.TAG)
                .commit()
        }
    }
}