package com.moviesroad.paulo.moviesroad.presentation.views.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moviesroad.paulo.moviesroad.R;
import com.moviesroad.paulo.moviesroad.data.entity.MovieDetailEntity;
import com.moviesroad.paulo.moviesroad.data.network.api.MovieRoadApi;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulo on 19/06/2017.
 */


public class MovieDetailFragment extends Fragment {

    @BindView(R.id.linear_layout_loading)
    LinearLayout loadingLayout;

    @BindView(R.id.image_view_header)
    ImageView headerImageView;

    @BindView(R.id.text_view_description)
    TextView descriptionTextView;

    private MovieRoadApi movieRoadApi;
    private long movieId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        movieRoadApi = MovieRoadApi.getInstance();
        setTitle();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMovieDetail();
    }

    private void getMovieDetail() {
        showLoading();
        movieRoadApi.getMovieDetail(movieId).enqueue(new Callback<MovieDetailEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(@NonNull Call<MovieDetailEntity> call, @NonNull Response<MovieDetailEntity> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    MovieDetailEntity movieDetailEntity = response.body();
                    if (movieDetailEntity != null) {
                        descriptionTextView.setText(movieDetailEntity.getDescription());
                        Picasso.with(getContext())
                                .load(movieDetailEntity.getDetailImageUrl())
                                .centerCrop()
                                .fit()
                                .into(headerImageView);
                    }
                } else {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFailure(@NonNull Call<MovieDetailEntity> call, @NonNull Throwable t) {
                hideLoading();
                Toast.makeText(getContext(), "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTitle() {
        if (getActivity() != null && getActivity().getIntent() != null && getActivity().getIntent().getExtras() != null) {
            getActivity().setTitle(getActivity().getIntent().getStringExtra("MOVIE_TITLE"));
            movieId = getActivity().getIntent().getLongExtra("MOVIE_ID", 0L);
        }
    }

    private void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

}
