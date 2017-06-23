package com.moviesroad.paulo.moviesroad.data.network.service;

import com.moviesroad.paulo.moviesroad.data.entity.LoginRequest;
import com.moviesroad.paulo.moviesroad.data.entity.MovieDetailEntity;
import com.moviesroad.paulo.moviesroad.data.entity.MovieListEntity;
import com.moviesroad.paulo.moviesroad.data.entity.UserEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by paulo on 19/06/2017.
 */

public interface MovieRoadService {

    @POST("auth/login")
    Call<UserEntity> doLogin(@Body LoginRequest loginRequest);

    @GET("movie/list")
    Call<MovieListEntity> getMovies(@Header("Authorization") String sessionToken);

    @GET("movie")
    Call<MovieDetailEntity> getMovieDetail(@Header("Authorization") String sessionToken, @Query("id") long movieId);
}
