package com.evaluation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaluation.countrylist.CountryQuery.Country

/**
 * @author Vladyslav Havrylenko
 * @since 16.03.2020
 */
class PageViewModel : ViewModel() {

    private var mLiveData = MutableLiveData<Country>()

    fun setResult(results: Country) {
        mLiveData.value = results
    }

    val result: LiveData<Country>
        get() = mLiveData
}