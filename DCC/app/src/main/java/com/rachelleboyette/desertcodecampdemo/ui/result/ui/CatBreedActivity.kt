package com.rachelleboyette.desertcodecampdemo.ui.result.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.ui.result.ui.fragment.CatBreedFragment

class CatBreedActivity : AppCompatActivity() {
    companion object {
        const val ARG_CAT = "arg_cat"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_breed_activity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CatBreedFragment.newInstance(intent))
            .commit()
    }
}