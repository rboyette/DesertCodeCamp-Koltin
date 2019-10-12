package com.rachelleboyette.desertcodecampdemo.ui.result.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.ui.components.AbstractFragment
import com.rachelleboyette.desertcodecampdemo.ui.result.ui.CatBreedActivity.Companion.ARG_CAT
import com.rachelleboyette.desertcodecampdemo.ui.result.viewmodel.BreedViewModel

class CatBreedFragment: AbstractFragment() {
    private lateinit var layout: View
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var countryOfOrigin: TextView
    private lateinit var description: TextView
    private lateinit var attributes: TextView
    private lateinit var weight: TextView
    private lateinit var averageLifeSpan: TextView

    private lateinit var viewModel: BreedViewModel

    private var cat: Cat? = null

    companion object {
        fun newInstance(intent: Intent) : CatBreedFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_CAT, intent.getParcelableExtra(ARG_CAT))
            val fragment = CatBreedFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null) {
            cat = arguments?.getParcelable(ARG_CAT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cat_breed_fragment, container, false)
        layout = view.findViewById(R.id.layout)
        image = view.findViewById(R.id.cat_image)
        title = view.findViewById(R.id.title)
        countryOfOrigin = view.findViewById(R.id.country_of_origin)
        description = view.findViewById(R.id.description)
        attributes = view.findViewById(R.id.attributes)
        weight = view.findViewById(R.id.weight)
        averageLifeSpan = view.findViewById(R.id.average_lifespan)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this,BreedViewModelFactory(cat))[BreedViewModel::class.java]
        viewModel.setState(MainApplication.state)
        viewModel.getData().observe(this, Observer {
            onDataReceived(it)
        })
        super.onActivityCreated(savedInstanceState)
    }

    private fun onDataReceived(viewState: BreedViewModel.ViewState) {
        if (viewState.isLoading) {
            showLoadingDialog()
        } else if (!viewState.isLoading && viewState.cat != null && viewState.imageData != null) {
            Glide.with(this).load(viewState.imageData).into(image)

            title.text = viewState.cat.name
            countryOfOrigin.text = getString(R.string.country_of_origin, viewState.cat.origin)
            description.text = viewState.cat.description
            attributes.text = viewState.cat.temperament
            weight.text = getString(R.string.weight_pounds, viewState.cat.weight.imperial)
            averageLifeSpan.text = getString(R.string.average_lifespan_years, viewState.cat.lifeSpan)
            layout.visibility = View.VISIBLE

            hideLoadingDialog()
        }
    }

    class BreedViewModelFactory(val cat: Cat?) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BreedViewModel(cat) as T
        }
    }
}