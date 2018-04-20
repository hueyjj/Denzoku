package com.hueyjj.denzoku.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hueyjj.denzoku.NyaaActivity;
import com.hueyjj.denzoku.R;
import com.hueyjj.denzoku.network.NyaaNetworkRequest;
import com.hueyjj.denzoku.parser.MalEntry;
import com.hueyjj.denzoku.parser.NyaaResult;

import java.util.ArrayList;

public class NyaaListFragment extends Fragment {

    public final String TAG = "NyaaListFragment";
    private ArrayList<NyaaResult> nyaaResult;
    private ContentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        nyaaResult = (MalEntry) getArguments().get(NyaaActivity.MAL_ENTRY);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        adapter = new ContentAdapter(recyclerView.getContext(), null);

        int episodeNum = (int) getArguments().get(NyaaActivity.EPISODE_NUM);
        Log.v(TAG, "Searching for episode number " + episodeNum);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public void setNewAdapterData(String query) {
        adapter.submitQuery(query);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView animeEpisode;
        public MalEntry malEntry;
        private int episodeNum;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.anime_episode_item_list, parent, false));
            animeEpisode = (TextView) itemView.findViewById(R.id.episode);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        public void setMalEntry(MalEntry malEntry) {
            this.malEntry = malEntry;
        }

        public void setEpisodeNum(int episodeNum) {
            this.episodeNum = episodeNum;
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> { // Set numbers of List in RecyclerView.
        public final String TAG = "NyaaContentAdapter";

        //FIXME Should different objects have different queue or one global queue is better or...?
        public RequestQueue queue;

        /* Number of items */
        private int length = 0;

        private MalEntry malEntry;

        public ContentAdapter(Context context, MalEntry malEntry) {
            queue = Volley.newRequestQueue(context);

            this.malEntry = malEntry;

            try {
                //length = Integer.parseInt(malEntry.seriesEpisodes);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //if (position < length) {
            //    holder.animeEpisode.setText(malEntry.seriesTitle + " " + position);
            //    holder.setMalEntry(malEntry);
            //    holder.setEpisodeNum(position);
            //}
        }

        @Override
        public void onViewAttachedToWindow(final ViewHolder holder) {
        }

        @Override
        public int getItemCount() {
            return length;
        }

        private void setLength(int length) {
            this.length = length;
        }


        public void submitQuery (String query) {
            NyaaNetworkRequest nyaaRequest = new NyaaNetworkRequest(Request.Method.GET, query,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v(TAG, "" + response.length());
                            Log.v(TAG, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v(TAG, "Failed to get nyaa list");
                            Log.v(TAG, error.getMessage());
                        }
                    });
            queue.add(nyaaRequest);
        }
    }
}
