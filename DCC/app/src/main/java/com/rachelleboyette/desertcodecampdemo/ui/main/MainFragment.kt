package com.rachelleboyette.desertcodecampdemo.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.ui.search.ui.ViewAllCatsActivity

class MainFragment : Fragment() {
    private lateinit var viewAllCats : LinearLayout
    private lateinit var mainThreadButton : RadioButton
    private lateinit var asyncTaskButton : RadioButton
    private lateinit var coroutinesButton : RadioButton
    private lateinit var executorButton : RadioButton

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.main_fragment, container, false)
        mainThreadButton = view.findViewById(R.id.main_thread_button)
        asyncTaskButton = view.findViewById(R.id.asynchronous_button)
        coroutinesButton = view.findViewById(R.id.coroutine_button)
        executorButton = view.findViewById(R.id.executor_button)

        viewAllCats = view.findViewById(R.id.view_all_cats)

        viewAllCats.setOnClickListener {
            startActivity(Intent(context, ViewAllCatsActivity::class.java))
        }

        mainThreadButton.setOnClickListener {
            MainApplication.state = MainApplication.SYNCHRONOUS
        }

        asyncTaskButton.setOnClickListener {
            MainApplication.state = MainApplication.ASYNC_TASK
        }

        coroutinesButton.setOnClickListener {
            MainApplication.state = MainApplication.COROUTINE
        }

        executorButton.setOnClickListener {
            MainApplication.state = MainApplication.EXECUTOR
        }

        return view
    }
}
