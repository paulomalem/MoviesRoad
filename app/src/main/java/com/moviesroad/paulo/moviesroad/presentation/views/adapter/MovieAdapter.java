package com.moviesroad.paulo.moviesroad.presentation.views.adapter;

/**
 * Created by paulo on 19/06/2017.
 */



import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviesroad.paulo.moviesroad.R;
import com.moviesroad.paulo.moviesroad.data.entity.MovieEntity;
import com.squareup.picasso.Picasso;
//import com.moviesroad.paulo.moviesroad.presentation.view.listeners.OnMovieClickListener;
//import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends ArrayAdapter {

    private List<MovieEntity> movieEntityList;
    private Context context;
    private LayoutInflater inflater;
    private OnMovieClickListener onMovieClickListener;


    public MovieAdapter(@NonNull Context context, @LayoutRes int resource, List<MovieEntity> movieEntityList, OnMovieClickListener onMovieClickListener) {
        super(context, resource);
        this.movieEntityList = movieEntityList;
        this.context = context;
        this.onMovieClickListener = onMovieClickListener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieViewHolder movieViewHolder;
        final MovieEntity movieEntity = movieEntityList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            movieViewHolder = new MovieViewHolder(convertView);
            convertView.setTag(movieViewHolder);
        } else {
            movieViewHolder = (MovieViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClicked(movieEntity.getId(), movieEntity.getTitle());
                }
            }
        });
        movieViewHolder.titleTextView.setText(movieEntity.getTitle());
        Picasso.with(context)
                .load(movieEntity.getCoverImageUrl())
                .centerCrop()
                .fit()
                .into(movieViewHolder.backgroundImageView);

        return convertView;
    }

    @Override
    public int getCount() {
        return movieEntityList.size();
    }

    @Override
    public MovieEntity getItem(int position) {
        return movieEntityList.get(position);
    }

    public class MovieViewHolder {
        @BindView(R.id.text_view_title)
        TextView titleTextView;

        @BindView(R.id.image_view_background)
        ImageView backgroundImageView;

        MovieViewHolder(View view) { ButterKnife.bind(this, view);  }
    }


}

