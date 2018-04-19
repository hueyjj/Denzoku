package com.hueyjj.denzoku.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.hueyjj.denzoku.MainActivity;
import com.hueyjj.denzoku.R;
import com.hueyjj.denzoku.network.MalNetworkRequest;
import com.hueyjj.denzoku.parser.MalEntry;
import com.hueyjj.denzoku.parser.MalParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimeListFragment extends Fragment {
    private final String TAG = "AnimeListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView animeImage;
        public TextView animeTitle;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.anime_item_list, parent, false));
            animeImage = (ImageView) itemView.findViewById(R.id.anime_thumbnail);
            animeTitle = (TextView) itemView.findViewById(R.id.anime_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Context context = v.getContext();
                    //Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    //context.startActivity(intent);
                }
            });
        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> { // Set numbers of List in RecyclerView.
        final String TAG = "ContentAdapter";

        private static final int LENGTH = 18;

        //FIXME Should different objects have different queue or one global queue is better or...?
        public RequestQueue queue;

        private ArrayList<String> animeTitles;
        private ArrayList<String> animeImages;
        //private final Drawable[] animeImages;

        public ContentAdapter(Context context) {
            animeTitles = new ArrayList<String>();
            animeImages = new ArrayList<String>();

            queue = Volley.newRequestQueue(context);
            //Resources resources = context.getResources();
            //TypedArray a = resources.obtainTypedArray(R.array.anime_thumbnail);
            //animeImages = new Drawable[a.length()];
            //for (int i = 0; i < animeImages.length; i++) {
            //    animeImages[i] = a.getDrawable(i);
            //}
            String apiUrl = MalNetworkRequest.createApiUrl("hueyjj");
            MalNetworkRequest malReq = new MalNetworkRequest(apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            initData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            queue.add(malReq);
            //a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.animeImage.setImageDrawable(animeImages[position % animeImages.length]);
            if (animeTitles.size() > 0) {
                holder.animeTitle.setText(animeTitles.get(position % animeTitles.size()));
            }
        }

        @Override
        public void onViewAttachedToWindow(final ViewHolder holder) {
            if (animeImages.size() <= 0) {
                return;
            }

            ImageRequest imageRequest = new ImageRequest(
                    animeImages.get(holder.getLayoutPosition() % animeImages.size()),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.animeImage.setImageBitmap(response);
                        }
                    },
                    0, 0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v(TAG, "Unable to download image for " + holder.animeTitle.getText());
                        }
                    }
            );
            this.queue.add(imageRequest);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        private void initData(String response) {
            MalParser parser = new MalParser();
            try {
                List<MalEntry> result = parser.parse(response);
                for (MalEntry entry : result) {
                    animeTitles.add(entry.seriesTitle);
                    animeImages.add(entry.seriesImage);
                }
                notifyDataSetChanged();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
