package com.moolajoo.waferchallenge;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moolajoo.waferchallenge.model.Country;

import java.util.List;

public class CountriesListAdapter extends BaseAdapter {

    private final List<Country> countries;
    private final Context context;

    public CountriesListAdapter(List<Country> countries, Context context) {
        this.countries = countries;
        this.context = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        if (countries != null)
            return countries.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.country_item_button, parent, false);


        Country country = countries.get(position);

        TextView name =
                view.findViewById(R.id.country_name);
        TextView currency =
                view.findViewById(R.id.country_currency);
        TextView language =
                view.findViewById(R.id.country_language);
        try {
            name.setText(country.getName());
            currency.setText(country.getCurrency());
            language.setText(country.getLanguage());

        } catch (Exception e) {
            Log.e("Adapter", e.getLocalizedMessage());
        }


        ImageButton delButton = view.findViewById(R.id.del_bomb_button);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        context.getString(R.string.swipe_removed) +
                                countries.get(position).getName(),
                        Toast.LENGTH_SHORT).show();

                MainActivity.visibility = new boolean[countries.size()];
                countries.remove(position);

                notifyDataSetChanged();

            }
        });

        if (MainActivity.visibility[position]) {
            delButton.setVisibility(View.VISIBLE);
        } else {
            delButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }


}