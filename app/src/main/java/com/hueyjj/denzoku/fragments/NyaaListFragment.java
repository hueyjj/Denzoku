package com.hueyjj.denzoku.fragments;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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
import com.android.volley.toolbox.Volley;
import com.hueyjj.denzoku.BuildConfig;
import com.hueyjj.denzoku.R;
import com.hueyjj.denzoku.downloader.TorrentRequest;
import com.hueyjj.denzoku.network.NyaaNetworkRequest;
import com.hueyjj.denzoku.parser.NyaaParser;
import com.hueyjj.denzoku.parser.NyaaResult;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        adapter = new ContentAdapter(recyclerView.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public void setNewAdapterData(String query) {
        adapter.submitQuery(query);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RequestQueue queue;

        private NyaaResult nyaaResult;

        public TextView title;
        public TextView seeders;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.nyaa_result_list, parent, false));
            title = (TextView) itemView.findViewById(R.id.title);
            seeders = (TextView) itemView.findViewById(R.id.seeders);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();

                    final Context appContext = context.getApplicationContext();

                    // Download torrent file
                    TorrentRequest torrentRequest = new TorrentRequest(Request.Method.GET, nyaaResult.torrentLink,
                            new Response.Listener<byte[]>() {
                                @Override
                                public void onResponse(byte[] response) {
                                    // Save torrent file
                                    String filename = nyaaResult.title + ".torrent";
                                    FileOutputStream outputstream;
                                    try {
                                        outputstream = appContext.openFileOutput(filename, Context.MODE_PRIVATE);
                                        outputstream.write(response);
                                        outputstream.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    File torrentFile = new File(appContext.getFilesDir(), filename);

                                    // Open torrent file in another application
                                    Uri path = FileProvider.getUriForFile(appContext,
                                            BuildConfig.APPLICATION_ID + ".provider",
                                            torrentFile);

                                    // Get torrent mime type
                                    ContentResolver cr = appContext.getContentResolver();
                                    String mime = cr.getType(path);

                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(path, mime);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    try {
                                        context.getApplicationContext().startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        //Log.v(TAG, "No application found to Unable to open torrent");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Log.v(TAG, "Error downloading torrent");
                                }
                            });
                    queue.add(torrentRequest);
                }
            });
        }

        public void setNyaaResult(NyaaResult nyaaResult) {
            this.nyaaResult = nyaaResult;
        }

        public void setRequestQueue(RequestQueue queue) {
            this.queue = queue;
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> { // Set numbers of List in RecyclerView.
        public final String TAG = "NyaaContentAdapter";

        //FIXME Should different objects have different queue or one global queue is better or...?
        public RequestQueue queue;

        private ArrayList<NyaaResult> nyaaResults;

        public ContentAdapter(Context context) {
            queue = Volley.newRequestQueue(context);

            nyaaResults = new ArrayList<NyaaResult>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemCount() > 0) {
                NyaaResult nyaaResult = nyaaResults.get(position % nyaaResults.size());
                holder.title.setText(nyaaResult.title);
                holder.seeders.setText(nyaaResult.seeders);
                holder.setNyaaResult(nyaaResult);
                holder.setRequestQueue(queue);
            }
        }

        @Override
        public void onViewAttachedToWindow(final ViewHolder holder) {
        }

        @Override
        public int getItemCount() {
            return nyaaResults.size();
        }

        public void submitQuery(String query) {
            NyaaNetworkRequest nyaaRequest = new NyaaNetworkRequest(Request.Method.GET, query,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            NyaaParser nyaaParser = new NyaaParser();
                            try {
                                nyaaResults = (ArrayList<NyaaResult>) nyaaParser.parse(response);
                                notifyDataSetChanged();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v(TAG, error.getMessage());
                        }
                    });
            queue.add(nyaaRequest);
        }
    }
}
