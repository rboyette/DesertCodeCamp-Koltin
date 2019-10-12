package com.rachelleboyette.desertcodecampdemo.ui.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import com.rachelleboyette.desertcodecampdemo.R

class LoadingFragment : DialogFragment() {
    companion object {
        const val TAG = "LoadingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AppCompatDialog(context, R.style.LoadingDialog)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}