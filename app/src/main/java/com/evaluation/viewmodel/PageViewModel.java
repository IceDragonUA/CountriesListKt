package com.evaluation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.evaluation.countrylist.CountryQuery;

/**
 * @author Vladyslav Havrylenko
 * @since 16.03.2020
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<CountryQuery.Country> mLiveData = new MutableLiveData<>();

    public void setResult(CountryQuery.Country results) {
        mLiveData.setValue(results);
    }

    public LiveData<CountryQuery.Country> getResult() {
        return mLiveData;
    }

}
