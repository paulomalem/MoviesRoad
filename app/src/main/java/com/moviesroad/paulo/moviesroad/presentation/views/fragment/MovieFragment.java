package com.moviesroad.paulo.moviesroad.presentation.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.moviesroad.paulo.moviesroad.R;
import com.moviesroad.paulo.moviesroad.data.entity.MovieListEntity;
import com.moviesroad.paulo.moviesroad.data.network.api.MovieRoadApi;
import com.moviesroad.paulo.moviesroad.presentation.views.activity.MovieDetailActivity;
import com.moviesroad.paulo.moviesroad.presentation.views.adapter.MovieAdapter;
import com.moviesroad.paulo.moviesroad.presentation.views.adapter.OnMovieClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment implements OnMovieClickListener {

    @BindView(R.id.list_view_movie)
    ListView movieListView;

    @BindView(R.id.linear_layout_loading)
    LinearLayout loadingLayout;

    MovieRoadApi movieRoadApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        movieRoadApi = MovieRoadApi.getInstance();
        getMovies();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getMovies();
    }

    private void getMovies() {
        showLoading();
        movieRoadApi.getMovies().enqueue(new Callback<MovieListEntity>() {
            @Override
            public void onResponse(@NonNull Call<MovieListEntity> call, @NonNull Response<MovieListEntity> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    MovieListEntity movieListEntity = response.body();
                    if (movieListEntity != null && movieListEntity.getMovies() != null) {
                        MovieAdapter movieAdapter = new MovieAdapter(getContext(), R.layout.item_movie, movieListEntity.getMovies(), MovieFragment.this);
                        movieListView.setAdapter(movieAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieListEntity> call, @NonNull Throwable t) {
                hideLoading();
                Toast.makeText(getContext(), "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading() {
      //  loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        //loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onMovieClicked(long movieId, String movieTitle) {
        //Toast.makeText(getContext(), "Clicado no movie" + movieId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        intent.putExtra("MOVIE_TITLE", movieTitle);
        startActivity(intent);
    }
}
