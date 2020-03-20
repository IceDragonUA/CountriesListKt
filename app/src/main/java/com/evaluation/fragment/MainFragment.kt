package com.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.evaluation.adapter.CustomListAdapter
import com.evaluation.command.ICommand
import com.evaluation.countrylist.CountryQuery
import com.evaluation.countrylist.CountryQuery.Country
import com.evaluation.countrylist.R
import com.evaluation.dagger.data.DataComponent
import com.evaluation.network.RestAdapter
import com.evaluation.viewmodel.PageViewModel
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
class MainFragment : Fragment() {

    private val TAG = MainFragment::class.java.canonicalName

    private lateinit var mPageViewModel: PageViewModel

    private var mRootView: View? = null

    @Inject
    lateinit var mRestAdapter: RestAdapter

    @BindView(R.id.countryList)
    lateinit var mCountriesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataComponent.Injector.component.inject(this)
        mPageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            val view = inflater.inflate(R.layout.main_layout, container, false)
            ButterKnife.bind(this, view)
            mRootView = view
            mCountriesList.layoutManager = LinearLayoutManager(activity)
            loadList()
        }
        return mRootView
    }

    private fun loadList() {
        mRestAdapter.restApiService.query(CountryQuery.builder().build())
            .enqueue(object : ApolloCall.Callback<CountryQuery.Data>() {
                override fun onResponse(response: Response<CountryQuery.Data>) {
                    activity?.runOnUiThread {
                        val adapter = response.data()?.countries()?.let {
                            CustomListAdapter(
                                it,
                                object : ICommand<Country> {
                                    override fun execute(param: Country) {
                                        mPageViewModel.setResult(param)
                                    }
                                }
                            )
                        }
                        mCountriesList.adapter = adapter
                        response.data()?.countries()?.get(0)?.let { mPageViewModel.setResult(it) }
                    }
                }

                override fun onFailure(throwable: ApolloException) {
                    Log.e(TAG, "Loading Error", throwable)
                }
            })
    }
}