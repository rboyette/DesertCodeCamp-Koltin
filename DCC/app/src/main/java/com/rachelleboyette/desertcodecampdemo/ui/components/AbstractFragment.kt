package com.rachelleboyette.desertcodecampdemo.ui.components

import androidx.fragment.app.Fragment

abstract class AbstractFragment : Fragment() {

    fun showLoadingDialog() {

        var loadingFragment = fragmentManager?.findFragmentByTag(LoadingFragment.TAG)

        if (loadingFragment == null || !loadingFragment.isVisible) {
            loadingFragment = LoadingFragment()

            loadingFragment.show(fragmentManager!!, LoadingFragment.TAG)
        }
    }

    fun hideLoadingDialog() {
        val loadingFragment = fragmentManager?.findFragmentByTag(LoadingFragment.TAG) as LoadingFragment?
        loadingFragment?.dismiss()
    }
}