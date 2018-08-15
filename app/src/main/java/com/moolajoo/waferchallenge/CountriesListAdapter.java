package com.moolajoo.waferchallenge;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

public class CountriesListAdapter extends BaseAdapter {

    private final List<JSONObject> countries;
    private final Context context;

    public CountriesListAdapter(List<JSONObject> countries, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);


        JSONObject country = countries.get(position);

        TextView name =
                view.findViewById(R.id.country_name);
        TextView currency =
                view.findViewById(R.id.country_currency);
        TextView language =
                view.findViewById(R.id.country_language);
        try {
            name.setText(country.getString("name"));

            currency.setText(country.getJSONArray("currencies")
                    .getJSONObject(0).getString("name"));
            language.setText(country.getJSONArray("languages")
                    .getJSONObject(0).getString("name"));
        } catch (Exception e) {
            Log.e("Adapter", e.getLocalizedMessage());
        }


        return view;
    }
}