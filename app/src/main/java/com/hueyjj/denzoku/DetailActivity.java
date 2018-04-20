package com.hueyjj.denzoku;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hueyjj.denzoku.fragments.EpisodeListFragment;
import com.hueyjj.denzoku.parser.MalEntry;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    public static final String MAL_ENTRY = "MAL_ENTRY";
    public MalEntry malEntry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        malEntry = (MalEntry) getIntent().getSerializableExtra(MAL_ENTRY);
        getSupportActionBar().setTitle(malEntry.seriesTitle);

        Bundle bundle = new Bundle();
        bundle.putSerializable(MAL_ENTRY, malEntry);

        EpisodeListFragment episodeListFragment = new EpisodeListFragment();
        episodeListFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, episodeListFragment).commit();
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
