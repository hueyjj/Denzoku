package com.hueyjj.denzoku;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hueyjj.denzoku.fragments.NyaaListFragment;
import com.hueyjj.denzoku.parser.MalEntry;

public class NyaaActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    public static final String MAL_ENTRY = "MAL_ENTRY";
    public static final String EPISODE_NUM = "EPISODE_NUM";
    public MalEntry malEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyaa);

        malEntry = (MalEntry) getIntent().getSerializableExtra(MAL_ENTRY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Nyaa: " + malEntry.seriesTitle);

        Bundle bundle = getIntent().getExtras();

        NyaaListFragment nyaaListFragment = new NyaaListFragment();
        nyaaListFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, nyaaListFragment).commit();

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
