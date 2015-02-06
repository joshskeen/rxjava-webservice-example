package com.joshskeen.rxjava_example.model;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

interface GithubAPIInterface {

    @GET("/users/{username}")
    public void requestUserDetails(@Path("username") String username, Callback<GithubUserDetail> callback);



    @GET("/users/{username}")
    public Observable<GithubUserDetail> rxRequestUserDetails(@Path("username") String username);

    @GET("/users")
    public void requestUsers(Callback<GithubUsersResponse> callback);

    @GET("/users")
    List<GithubUser> requestUsers();

    @GET("/users")
    public Observable<List<GithubUser>> rxRequestUsers();

}
