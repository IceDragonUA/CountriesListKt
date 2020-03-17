package com.evaluation.dagger.data

import com.evaluation.fragment.MainFragment

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
internal interface DataGraph {
    fun inject(mainFragment: MainFragment)
}