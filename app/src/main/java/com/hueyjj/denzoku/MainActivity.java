package com.hueyjj.denzoku;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hueyjj.denzoku.downloader.TorrentDownloader;
import com.hueyjj.denzoku.fragments.AnimeListFragment;
import com.hueyjj.denzoku.network.MalNetworkRequest;
import com.hueyjj.denzoku.network.NyaaNetworkRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
        //navigationView.setNavigationItemSelectedListener(this);

        //Button btn = findViewById(R.id.button);
        //btn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        String url = "https://nyaa.si/download/1026506.torrent";
        //        TorrentDownloader req = new TorrentDownloader(Request.Method.GET, url,
        //                new Response.Listener<byte[]>() {
        //                    @Override
        //                    public void onResponse(byte[] response) {
        //                        // Write to file
        //                        String filename = "TorrentExample.torrent";
        //                        FileOutputStream outputstream;
        //                        try {
        //                            outputstream = openFileOutput(filename, Context.MODE_PRIVATE);
        //                            outputstream.write(response);
        //                            outputstream.close();
        //                        } catch (FileNotFoundException e) {
        //                            e.printStackTrace();
        //                        } catch (IOException e) {
        //                            e.printStackTrace();
        //                        }

        //                        Log.v(TAG, "Torrent download success");
        //                    }
        //                },
        //                new Response.ErrorListener() {
        //                    @Override
        //                    public void onErrorResponse(VolleyError error) {
        //                        Log.v(TAG, "Error downloading torrent");
        //                    }
        //                });
        //        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //        queue.add(req);
        //    }
        //});

        //File fileDir = this.getFilesDir();
        //Log.v(TAG, "file directory: " + fileDir.getAbsolutePath());

        //File torrentFile = new File(this.getFilesDir(),"TorrentExample.torrent");
        //Log.v(TAG, "TorrentExample.torrent path: " + torrentFile.getPath());

        //if (torrentFile.exists()) {
        //    Log.v(TAG, "TorrentExample.torrent file exists");
        //    Uri path = FileProvider.getUriForFile(this,
        //            BuildConfig.APPLICATION_ID + ".provider",
        //            torrentFile);
        //    Log.v(TAG, "URI: " + path.getPath());

        //    // Try to get torrent mime type
        //    ContentResolver cr = this.getContentResolver();
        //    String mime = cr.getType(path);

        //    Intent intent = new Intent(Intent.ACTION_VIEW);
        //    intent.setDataAndType(path, mime);
        //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //    try {
        //        startActivity(intent);
        //    } catch (ActivityNotFoundException e)  {
        //       Log.v(TAG, "No application found to Unable to open torrent");
        //    }

        //}
        //btn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        NyaaNetworkRequest req = new NyaaNetworkRequest(getApplicationContext());
        //        req.setQuery("Boku no hero academia");
        //        req.build();
        //        req.getNyaaList();
        //    }
        //});
        //btn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        MalNetworkRequest req = new MalNetworkRequest(getApplicationContext(), "hueyjj");
        //        req.getAnimeList();
        //    }
        //});
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();

//        if (id == R.id.nav_anime_list) {
//            // Handle the camera action
//        } else if (id == R.id.nav_airing) {

//        } else if (id == R.id.nav_nyaa_rss) {

//        } else if (id == R.id.nav_airing) {

//        } else if (id == R.id.nav_about) {

//        } else if (id == R.id.nav_setting) {

//        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_anime_list:
                fragmentClass = AnimeListFragment.class;
                break;
            default:
                fragmentClass = AnimeListFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}
