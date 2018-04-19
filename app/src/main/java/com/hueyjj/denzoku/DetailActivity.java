package com.hueyjj.denzoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hueyjj.denzoku.parser.MalEntry;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    public static final String MAL_ENTRY = "MAL_ENTRY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MalEntry malEntry = (MalEntry) getIntent().getSerializableExtra(MAL_ENTRY);
        getSupportActionBar().setTitle(malEntry.seriesTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
       return super.onOptionsItemSelected(item);
    }
}
