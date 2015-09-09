package com.joshskeen.rxjava_example.model;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

interface GithubAPIInterface {

    @GET("/users/{username}")
    void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback);

    @GET("/users/{username}")
    Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username);

    @GET("/users")
    void requestUsers(@Query("per_page") Integer perPage, Callback<List<GithubUser>> callback);

    @GET("/users")
    List<GithubUser> requestUsers();

    @GET("/users")
    Observable<List<GithubUser>> rxRequestUsers(@Query("per_page") Integer perPage);
}
