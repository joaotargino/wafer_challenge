package com.moolajoo.waferchallenge;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.moolajoo.waferchallenge.model.Country;
import com.moolajoo.waferchallenge.network.OnTaskCompleted;
import com.moolajoo.waferchallenge.network.RetrieveCountriesTask;
import com.moolajoo.waferchallenge.utils.SwipeToDelete;

import java.util.ArrayList;
import java.util.List;

import static com.moolajoo.waferchallenge.utils.VerifyNetwork.isNetworkConnected;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    private ListView listView;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String LIST_KEY = "listOfCountries";


    private CountriesListAdapter adapter;
    private ArrayList<Country> mCountries = new ArrayList<>();
    private SwipeToDelete swipeDetected = new SwipeToDelete();

    Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvCountries);


        if (mListState != null) {
            listView.onRestoreInstanceState(mListState);

        }


        listView.setOnTouchListener(swipeDetected);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (swipeDetected.swipeDetected()) {
                    if (swipeDetected.getAction() == SwipeToDelete.Action.RL) {
                        String country = "";
                        try {
                            country = mCountries.get(i).getName();
                        } catch (Exception e) {
                            Log.e(LOG_TAG, e.getMessage());
                        }
                        Toast.makeText(MainActivity.this,
                                getString(R.string.swipe_removed) + country,
                                Toast.LENGTH_SHORT).show();
                        mCountries.remove(i);
                        adapter.notifyDataSetChanged();

                    } else {

                    }
                }


            }
        });

    }


    @Override
    public void onTaskCompleted(List<Country> countries) {
        if (countries != null) {
            mCountries.addAll(countries);

            adapter =
                    new CountriesListAdapter(mCountries, this);

            listView.setAdapter(adapter);


        } else {
            Toast.makeText(MainActivity.this,
                    R.string.failed_load_data,
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = listView.onSaveInstanceState();
        outState.putParcelable(LIST_KEY, mListState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mListState = state.getParcelable(LIST_KEY);
        listView.onRestoreInstanceState(mListState);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnected(this)) {
            loadData();
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.failed_load_data,
                    Toast.LENGTH_LONG).show();
        }

        if (mListState != null) {
            listView.onRestoreInstanceState(mListState);
            mListState = null;
        }

    }


    public void loadData() {
        RetrieveCountriesTask countriesTask = new RetrieveCountriesTask(this);
        countriesTask.execute();
    }
}
