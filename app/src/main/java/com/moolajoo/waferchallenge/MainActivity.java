package com.moolajoo.waferchallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.moolajoo.waferchallenge.network.OnTaskCompleted;
import com.moolajoo.waferchallenge.network.RetrieveCountriesTask;
import com.moolajoo.waferchallenge.utils.SwipeToDelete;

import org.json.JSONObject;

import java.util.List;

import static com.moolajoo.waferchallenge.utils.VerifyNetwork.isNetworkConnected;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    private ListView listView;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private CountriesListAdapter adapter;
    private List<JSONObject> mCountries;
    private SwipeToDelete swipeDetected = new SwipeToDelete();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvCountries);


        if (isNetworkConnected(this)) {
            RetrieveCountriesTask countriesTask = new RetrieveCountriesTask(this);
            countriesTask.execute();
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.failed_load_data,
                    Toast.LENGTH_LONG).show();
        }


        listView.setOnTouchListener(swipeDetected);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (swipeDetected.swipeDetected()) {
                    if (swipeDetected.getAction() == SwipeToDelete.Action.RL) {
                        Log.d(LOG_TAG, "R to L");
                        String country = "";
                        try {
                            country = mCountries.get(i).getString("name");
                        } catch (Exception e) {
                            Log.e(LOG_TAG, e.getMessage());
                        }
                        Toast.makeText(MainActivity.this,
                                "Removed " + country,
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
    public void onTaskCompleted(List<JSONObject> countries) {
        if (countries != null) {
            mCountries = countries;
            adapter =
                    new CountriesListAdapter(mCountries, this);

            listView.setAdapter(adapter);


        } else {
            Toast.makeText(MainActivity.this,
                    R.string.failed_load_data,
                    Toast.LENGTH_LONG).show();
        }

    }
}
