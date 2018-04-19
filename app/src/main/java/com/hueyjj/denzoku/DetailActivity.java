package com.hueyjj.denzoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hueyjj.denzoku.parser.MalEntry;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    public static final String MAL_ENTRY = "MAL_ENTRY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MalEntry malEntry = (MalEntry) getIntent().getSerializableExtra(MAL_ENTRY);
        Log.v(TAG, "In malEntry: " + malEntry.seriesStatus);
    }
}
