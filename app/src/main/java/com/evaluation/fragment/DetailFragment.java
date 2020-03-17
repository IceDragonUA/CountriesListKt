package com.evaluation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.evaluation.countrylist.R;
import com.evaluation.utils.StringUtils;
import com.evaluation.viewmodel.PageViewModel;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
public class DetailFragment extends Fragment {

    public final String TAG = DetailFragment.class.getCanonicalName();

    private PageViewModel mPageViewModel;

    private View mRootView;

    @BindView(R.id.name)
    TextView mNameView;

    @BindView(R.id.native_)
    TextView mNativeView;

    @BindView(R.id.emoji)
    TextView mEmojiView;

    @BindView(R.id.currency)
    TextView mCurrencyView;

    @BindView(R.id.phone)
    TextView mPhoneView;

    @BindView(R.id.continent)
    TextView mContinentView;

    @BindView(R.id.language)
    TextView mLanguageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.info_layout, container, false);
            ButterKnife.bind(this, mRootView);
            loadInfo();
        }
        return mRootView;
    }

    private void loadInfo() {
        mPageViewModel.getResult().observe(requireActivity(), result -> {
            mNameView.setText(result.name());
            mNativeView.setText(result.native_());
            mEmojiView.setText(result.emoji());
            mCurrencyView.setText(result.currency());
            mPhoneView.setText(result.phone());
            mContinentView.setText(result.continent().name());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.languages().size(); i++) {
                sb.append(result.languages().get(i).name());
                if (i < result.languages().size() - 1) {
                    sb.append(StringUtils.COMMA_STRING);
                    sb.append(StringUtils.BLANK_STRING);
                }
            }
            mLanguageView.setText(sb.toString());
        });
    }
}
