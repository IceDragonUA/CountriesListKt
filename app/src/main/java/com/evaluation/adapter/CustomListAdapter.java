package com.evaluation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.evaluation.command.ICommand;
import com.evaluation.countrylist.CountryQuery;
import com.evaluation.countrylist.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ListAdapterHolder> {

    private final List<CountryQuery.Country> mCountriesList;
    private final ICommand<CountryQuery.Country> mClickCommand;


    public CustomListAdapter(List<CountryQuery.Country> countriesList, ICommand<CountryQuery.Country> clickCommand) {
        this.mCountriesList = countriesList;
        this.mClickCommand = clickCommand;
    }

    @NotNull
    @Override
    public ListAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ListAdapterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ListAdapterHolder countriesListAdapterHolder, int position) {
        countriesListAdapterHolder.bind(getItem(position), mClickCommand);
    }


    private CountryQuery.Country getItem(int position) {
        return mCountriesList.get(position);
    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }

    static class ListAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView titleView;

        ListAdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final CountryQuery.Country userList, ICommand<CountryQuery.Country> clickCommand) {

            itemView.setOnClickListener(v -> clickCommand.execute(userList));

            titleView.setText(userList.name());
        }
    }
}
