package com.hueyjj.denzoku;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hueyjj.denzoku.fragments.NyaaListFragment;
import com.hueyjj.denzoku.parser.MalEntry;
import com.hueyjj.denzoku.parser.NyaaQuery;
import com.hueyjj.denzoku.parser.NyaaQueryBuilder;

public class NyaaActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    public static final String MAL_ENTRY = "MAL_ENTRY";
    public static final String EPISODE_NUM = "EPISODE_NUM";
    public MalEntry malEntry;
    public NyaaListFragment nyaaListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyaa);

        malEntry = (MalEntry) getIntent().getSerializableExtra(MAL_ENTRY);

        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Nyaa: " + malEntry.seriesTitle + " " + bundle.get(EPISODE_NUM));


        nyaaListFragment = new NyaaListFragment();
        nyaaListFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, nyaaListFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nyaa_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.nyaa_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Show options for search
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v(TAG, "Search query: " + query);
                menu.findItem(R.id.nyaa_search).collapseActionView();

                // TODO Set options here in builder
                NyaaQuery nyaaQuery = new NyaaQueryBuilder()
                        .query(query)
                        .sort(NyaaQuery.Sort.SEEDERS)
                        .pageNum("0")
                        .buildNyaaQuery();
                nyaaListFragment.setNewAdapterData(nyaaQuery.toString());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
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
