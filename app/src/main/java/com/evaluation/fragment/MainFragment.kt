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
import com.evaluation.countrylist.MainActivity
import com.evaluation.countrylist.R
import com.evaluation.dagger.data.DataComponent.Injector.component
import com.evaluation.network.RestAdapter
import com.evaluation.viewmodel.PageViewModel
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
class MainFragment : Fragment() {

    private val TAG = MainFragment::class.java.canonicalName

    lateinit var mPageViewModel: PageViewModel

    lateinit var mActivity: MainActivity

    private var mRootView: View? = null

    @Inject
    lateinit var mRestAdapter: RestAdapter

    @BindView(R.id.countryList)
    lateinit var mCountriesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity
    }

    private fun loadList() {
        mRestAdapter.restApiService.query(
                CountryQuery.builder().build()
            )
            .enqueue(object : ApolloCall.Callback<CountryQuery.Data>() {
                override fun onResponse(response: Response<CountryQuery.Data>) {
                    mActivity.runOnUiThread {
                        val adapter = CustomListAdapter(
                            response.data()!!.countries()!!,
                            object : ICommand<Country> {
                                override fun execute(param: Country) {
                                    mPageViewModel.setResult(param)
                                }
                            }
                        )
                        mCountriesList.adapter = adapter
                        mPageViewModel.setResult(response.data()!!.countries()!![0])
                    }
                }

                override fun onFailure(throwable: ApolloException) {
                    Log.e(TAG, "Loading Error", throwable)
                }
            })
    }
}