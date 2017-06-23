package com.moviesroad.paulo.moviesroad.data.network.api;

import com.moviesroad.paulo.moviesroad.BuildConfig;
import com.moviesroad.paulo.moviesroad.data.entity.LoginRequest;
import com.moviesroad.paulo.moviesroad.data.entity.MovieDetailEntity;
import com.moviesroad.paulo.moviesroad.data.entity.MovieEntity;
import com.moviesroad.paulo.moviesroad.data.entity.MovieListEntity;
import com.moviesroad.paulo.moviesroad.data.entity.UserEntity;
import com.moviesroad.paulo.moviesroad.data.network.service.MovieRoadService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulo on 19/06/2017.
 */

public class MovieRoadApi {

    private MovieRoadService movieRoadService;
    private static MovieRoadApi instance;
    private String sessionToken;

    public static MovieRoadApi getInstance() {
        if (instance == null) {
            instance = new MovieRoadApi();
        }

        return  instance;
    }

    private MovieRoadApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(defaultConverterFactory())
                .build();

        this.movieRoadService = retrofit.create(MovieRoadService.class);
    }

    private Converter.Factory defaultConverterFactory() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return GsonConverterFactory.create(gson);
    }

    public Call<UserEntity> doLogin(LoginRequest loginRequest) {
        return movieRoadService.doLogin(loginRequest);
    }

    public Call<MovieListEntity> getMovies() {
        return movieRoadService.getMovies(getSessionToken());
    }

    public Call<MovieDetailEntity> getMovieDetail(long movieId) {
        return movieRoadService.getMovieDetail(getSessionToken(), movieId);
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }




}
