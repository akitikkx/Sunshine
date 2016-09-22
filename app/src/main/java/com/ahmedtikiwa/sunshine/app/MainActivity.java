package com.ahmedtikiwa.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new PlaceHolderFragment())
                    .commit();
        }
    }

    public static class PlaceHolderFragment extends Fragment {
        ArrayAdapter mAdapter;

        public PlaceHolderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, viewGroup, false);
            ListView listView = (ListView) view.findViewById(R.id.list_view_forecast);

            // fake weather string data to be used to populate the adapter
            String[] fakeData = {
                    "Today - Sunny - 14/20",
                    "Tomorrow - Sunny - 14/25",
                    "Saturday - Rain - 14/18",
                    "Sunday - Sunny - 14/20",
            };

            // an array list of the fake data created above
            List<String> fakeDataArray = new ArrayList<String>(Arrays.asList(fakeData));

            // initialize the array adapter
            mAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview,
                    fakeDataArray);

            // assign the adapter to the listview
            listView.setAdapter(mAdapter);

            return view;
        }
    }
}
