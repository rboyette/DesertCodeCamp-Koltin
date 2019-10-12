package com.rachelleboyette.desertcodecampdemo.ui.search.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rachelleboyette.desertcodecampdemo.R
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.ui.search.viewmodel.ViewAllCatsViewModel

class CatListAdapter(private val viewModel: ViewAllCatsViewModel,
                     private val data: List<Cat>) : RecyclerView.Adapter<CatListAdapter.CatListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cat_breed, parent, false)
        return CatListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CatListViewHolder, position: Int) {
        val cat = data[position]

        holder.breed.text = cat.name
        holder.layout.setOnClickListener {
            viewModel.onCatBreedClicked(cat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    class CatListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var breed: TextView = view.findViewById(R.id.cat_breed)
        var layout: View = view.findViewById(R.id.layout)
    }
}