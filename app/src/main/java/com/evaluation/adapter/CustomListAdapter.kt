package com.evaluation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.evaluation.adapter.CustomListAdapter.ListAdapterHolder
import com.evaluation.command.ICommand
import com.evaluation.countrylist.CountryQuery.Country
import com.evaluation.countrylist.R

class CustomListAdapter(private val mCountriesList: List<Country>, private val clickCommand: ICommand<Country>) : RecyclerView.Adapter<ListAdapterHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListAdapterHolder =
        ListAdapterHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(countriesListAdapterHolder: ListAdapterHolder, position: Int) {
        countriesListAdapterHolder.bind(getItem(position), clickCommand)
    }

    private fun getItem(position: Int): Country {
        return mCountriesList[position]
    }

    override fun getItemCount(): Int {
        return mCountriesList.size
    }

    class ListAdapterHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        @BindView(R.id.name)
        lateinit var titleView: TextView

        init {
            ButterKnife.bind(this, view!!)
        }

        fun bind(userList: Country, clickCommand: ICommand<Country>) {
            itemView.setOnClickListener { clickCommand.execute(userList) }
            titleView.text = userList.name()
        }
    }
}