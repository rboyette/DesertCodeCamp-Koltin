package com.rachelleboyette.desertcodecampdemo.ui.search.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.ui.components.AbstractFragment
import com.rachelleboyette.desertcodecampdemo.ui.result.ui.CatBreedActivity
import com.rachelleboyette.desertcodecampdemo.ui.result.ui.CatBreedActivity.Companion.ARG_CAT
import com.rachelleboyette.desertcodecampdemo.ui.search.ui.adapter.CatListAdapter
import com.rachelleboyette.desertcodecampdemo.ui.search.viewmodel.ViewAllCatsViewModel

class ViewAllCatsFragment : AbstractFragment() {

    private lateinit var viewModel: ViewAllCatsViewModel
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "ViewAllCatsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_all_cats_fragment, container, false)

        recyclerView = view.findViewById(R.id.cat_breeds)
        recyclerView.visibility = View.GONE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ViewAllCatsViewModel::class.java]
        viewModel.getData().observe(this, Observer {
            onViewStateReceived(it)
        })
        viewModel.getCatClickedData().observe(this, Observer {
            onCatClicked(it)
        })
        viewModel.setState(MainApplication.state)

        super.onActivityCreated(savedInstanceState)
    }

    private fun onCatClicked(cat: Cat?) {
        val intent = Intent(context, CatBreedActivity::class.java)
        intent.putExtra(ARG_CAT, cat)

        startActivity(intent)
    }

    private fun onViewStateReceived(viewState: ViewAllCatsViewModel.ViewState) {
        if (viewState.isLoading) {
            showLoadingDialog()
        } else if (!viewState.isLoading && viewState.data != null) {
            val adapter = CatListAdapter(viewModel, viewState.data)
            recyclerView.layoutManager = LinearLayoutManager(context!!)

            recyclerView.adapter = adapter
            recyclerView.visibility = View.VISIBLE
            hideLoadingDialog()
        }
    }
}
