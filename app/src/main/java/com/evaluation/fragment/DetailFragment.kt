package com.evaluation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.evaluation.countrylist.CountryQuery.Country
import com.evaluation.countrylist.R
import com.evaluation.utils.StringUtils
import com.evaluation.viewmodel.PageViewModel

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
class DetailFragment : Fragment() {

    val TAG = DetailFragment::class.java.canonicalName

    private lateinit var mPageViewModel: PageViewModel

    private var mRootView: View? = null

    @BindView(R.id.name)
    lateinit var mNameView: TextView

    @BindView(R.id.native_)
    lateinit var mNativeView: TextView

    @BindView(R.id.emoji)
    lateinit var mEmojiView: TextView

    @BindView(R.id.currency)
    lateinit var mCurrencyView: TextView

    @BindView(R.id.phone)
    lateinit var mPhoneView: TextView

    @BindView(R.id.continent)
    lateinit var mContinentView: TextView

    @BindView(R.id.language)
    lateinit var mLanguageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            val view =  inflater.inflate(R.layout.info_layout, container, false)
            ButterKnife.bind(this, view)
            mRootView = view
            loadInfo()
        }
        return mRootView
    }

    private fun loadInfo() {
        mPageViewModel.result.observe(requireActivity(),
            Observer { result ->
                mNameView.text = result.name()
                mNativeView.text = result.native_()
                mEmojiView.text = result.emoji()
                mCurrencyView.text = result.currency()
                mPhoneView.text = result.phone()
                mContinentView.text = result.continent()?.name() ?: StringUtils.EMPTY_STRING
                val sb = StringBuilder()
                result.languages()?.indices?.let {
                    for (i in it) {
                        result.languages()?.let { list ->
                            sb.append(list[i]?.name() ?: StringUtils.EMPTY_STRING)
                            if (list[i]?.name() != null) {
                                result.languages()?.size?.let { size ->
                                    if (i < size - 1) {
                                        sb.append(StringUtils.COMMA_STRING)
                                        sb.append(StringUtils.BLANK_STRING)
                                    }
                                }
                            }
                        }

                    }
                }

                mLanguageView.text = sb.toString()
            }
        )
    }
}