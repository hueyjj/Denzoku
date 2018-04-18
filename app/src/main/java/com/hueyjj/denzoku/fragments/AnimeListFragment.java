package com.hueyjj.denzoku.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

import com.hueyjj.denzoku.R;

public class AnimeListFragment extends Fragment {
    private final String TAG = "AnimeListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_anime_list,
        //        container, false);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView animeThumbnail;
        public TextView animeTitle;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.anime_item_list, parent, false));
            animeThumbnail = (ImageView) itemView.findViewById(R.id.anime_thumbnail);
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
        private static final int LENGTH = 18;

        private final String[] animeTitles;
        private final Drawable[] animeThumbnails;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            animeTitles = new String[] {
                    "Boku no Hero Academia",
                    "Ergo Proxy",
                    "Death Note",
            };

            TypedArray a = resources.obtainTypedArray(R.array.anime_thumbnail);
            animeThumbnails = new Drawable[a.length()];
            for (int i = 0; i < animeThumbnails.length; i++) {
                animeThumbnails[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.animeThumbnail.setImageDrawable(animeThumbnails[position % animeThumbnails.length]);
            holder.animeTitle.setText(animeTitles[position % animeTitles.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
