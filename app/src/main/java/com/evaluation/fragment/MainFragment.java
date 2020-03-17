package com.evaluation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.evaluation.adapter.CustomListAdapter;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.network.RestAdapter;
import com.evaluation.countrylist.CountryQuery;
import com.evaluation.countrylist.MainActivity;
import com.evaluation.countrylist.R;
import com.evaluation.viewmodel.PageViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
public class MainFragment extends Fragment {

    private final String TAG = MainFragment.class.getCanonicalName();

    private PageViewModel mPageViewModel;

    private MainActivity mActivity;

    private View mRootView;

    @Inject
    RestAdapter mRestAdapter;

    @BindView(R.id.countryList)
    RecyclerView mCountriesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.getComponent().inject(this);
        mPageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.main_layout, container, false);
            ButterKnife.bind(this, mRootView);
            mCountriesList.setLayoutManager(new LinearLayoutManager(mActivity));
            loadList();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    private void loadList() {
        mRestAdapter.getRestApiService().query(CountryQuery.builder().build())
                .enqueue(new ApolloCall.Callback<CountryQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<CountryQuery.Data> response) {
                        mActivity.runOnUiThread(() -> {
                            CustomListAdapter adapter = new CustomListAdapter(
                                    response.data().countries(),
                                    selectedItem -> mPageViewModel.setResult(selectedItem)
                            );
                            mCountriesList.setAdapter(adapter);
                            mPageViewModel.setResult(response.data().countries().get(0));
                        });

                    }

                    @Override
                    public void onFailure(@NotNull ApolloException throwable) {
                        Log.e(TAG, "Loading Error", throwable);
                    }
                });
    }
}
